package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dao.*;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.entity.*;
import top.tosim.actrainer.service.ContestService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/contests")
public class ContestController {
    Logger log = LoggerFactory.getLogger(ContestController.class);
    @Autowired
    UserDao userDao;
    @Autowired
    ContestDao contestDao;
    @Autowired
    ContestService contestService;
    @Autowired
    EditProblemDao editProblemDao;
    @Autowired
    ProblemDao problemDao;
    @Autowired
    SubmissionDao submissionDao;

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getContestTotalCount(ContestPageSelectDto pageSelectDto){
        return contestService.getContestTotalCount(pageSelectDto);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestList(ContestPageSelectDto pageSelectDto){
        log.info(JSON.toJSONString(pageSelectDto));
        RespJson respJson = new RespJson();
        respJson.setObj(contestService.getContestList(pageSelectDto));
        respJson.setSuccess(1);
        return respJson;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestProblemList(@PathVariable("id") Integer id,String password){
        RespJson respJson = new RespJson();
        Contest contest = contestDao.selectByPrimaryKey(id);
        if(contest.getPassword() != null && password != null && !contest.getPassword().equals(password)){
            respJson.setSuccess(0);
            return respJson;
        }
        List<Problem> containProblems = new ArrayList<Problem>();

        List<ContestProblem> contestProblemList = contestDao.selectRowsByContestId(contest.getId());
        for(ContestProblem contestProblem: contestProblemList) {
            if (contestProblem.getEditedProblemId() != null) {
                Problem editedProblem = editProblemDao.selectByPrimaryKey(contestProblem.getEditedProblemId());
                editedProblem.setIsEdited(1);
                containProblems.add(editedProblem);
            } else {
                Problem problem = problemDao.selectByOjAndPid(contestProblem.getRemoteOj(), contestProblem.getRemoteProblemId() + "");
                problem.setIsEdited(0);
                containProblems.add(problem);
            }
        }
        contest.setContainProblems(containProblems);
        respJson.setSuccess(1);
        respJson.setObj(contest);
        return respJson;
    }

    @RequestMapping(value = "/{id}/{remoteOj}/{remoteProblemId}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestProblem(@PathVariable("id") Integer id,@PathVariable("remoteOj") String remoteOj,@PathVariable("remoteProblemId") Integer remoteProblemId){
        log.info(id + " " + remoteOj + " " + remoteProblemId);
        RespJson respJson = new RespJson();
        ContestProblem contestProblem = contestDao.selectRow(id,remoteOj,remoteProblemId);
        if(contestProblem.getEditedProblemId() == null){
            respJson.setObj(problemDao.selectByOjAndPid(remoteOj,remoteProblemId+""));
        }else{
            respJson.setObj(editProblemDao.selectByPrimaryKey(contestProblem.getEditedProblemId()));
        }
        respJson.setSuccess(1);
        return respJson;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public RespJson createContest(HttpServletRequest request, @RequestBody Contest contest){
        log.info(JSON.toJSONString(contest));
        RespJson respJson = new RespJson();
        User user = (User)request.getSession(true).getAttribute("user");
        if(user == null){
            respJson.setSuccess(0);
            return respJson;
        }
        if(contest.getPassword().trim().equals("")) contest.setPassword(null);

        contest.setUserId(user.getId());
        contestDao.insertSelective(contest);
        for(int ix = 0;ix < contest.getContainProblems().size();ix++){
            Problem problem = contest.getContainProblems().get(ix);
            ContestProblem contestProblem = new ContestProblem();
            contestProblem.setContestId(contest.getId());
            contestProblem.setRemoteOj(problem.getRemoteOj());
            contestProblem.setRemoteProblemId(Integer.parseInt(problem.getRemoteProblemId()));
            if(problem.getIsEdited() != null && problem.getIsEdited().equals(1)){
                //editedId = insert to editProblems
                problem.setId(null);
                contestDao.insertEditedProblemSelective(problem);
                contestProblem.setEditedProblemId(problem.getId());
            }
            log.info(JSON.toJSONString(contestProblem));
            contestProblem.setIndex(ix);
            contestDao.insertIntoContestProblem(contestProblem);
        }
        respJson.setSuccess(1);
        return respJson;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @ResponseBody
    public RespJson updateContest(HttpServletRequest request, @PathVariable("id") int id,@RequestBody Contest contest){
        log.info(JSON.toJSONString(contest));
        RespJson respJson = new RespJson();
        User user = (User)request.getSession(true).getAttribute("user");
        if(user == null || !(user.getId().equals(contest.getUserId())) ){
            respJson.setSuccess(0);
            respJson.setMsg("not login or not own this contest");
            return respJson;
        }
        int contestId = id;
        contest.setId(contestId);
        contestDao.updateByPrimaryKeySelective(contest);
        List<ContestProblem> contestProblemList = contestDao.selectRowsByContestId(contest.getId());
        for(ContestProblem cp :contestProblemList){
            if(cp.getEditedProblemId() != null){
                editProblemDao.deleteByPrimaryKey(cp.getEditedProblemId());
            }
        }
        contestDao.deleteContestProblemByCid(contestId);
        for(int ix = 0;ix < contest.getContainProblems().size();ix++){
            Problem problem = contest.getContainProblems().get(ix);
            ContestProblem contestProblem = new ContestProblem();
            contestProblem.setContestId(contest.getId());
            contestProblem.setRemoteOj(problem.getRemoteOj());
            contestProblem.setRemoteProblemId(Integer.parseInt(problem.getRemoteProblemId()));
            if(problem.getIsEdited() != null && problem.getIsEdited().equals(1)){
                problem.setId(null);
                contestDao.insertEditedProblemSelective(problem);
                contestProblem.setEditedProblemId(problem.getId());
            }
            contestProblem.setIndex(ix);
            contestDao.insertIntoContestProblem(contestProblem);
        }
        respJson.setSuccess(1);
        return respJson;
    }

    @RequestMapping(value = "/{id}/rank",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestRank(@PathVariable("id") int id){
        RespJson respJson = new RespJson();
        Contest contest = contestDao.selectByPrimaryKey(id);
        List<Submission> submissionList = submissionDao.selectByContestId(id);
        Map<Integer,String> parts = new HashMap<Integer, String>();
        List<List> submit = new ArrayList();
        for(Submission submission:submissionList){
            List sub = new ArrayList();
            sub.add(submission.getUserId());
            sub.add(submission.getIndex());
            sub.add(submission.getStatus().equals("AC")?1:0);
            sub.add(submission.getSubmitTime().getTime() - contest.getStartTime());
            if(!parts.containsKey(submission.getUserId())){
                User u = userDao.selectByPrimaryKey(submission.getUserId());
                parts.put(submission.getUserId(),u.getAccountName());
            }
            submit.add(sub);
        }
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("parts",parts);
        res.put("submit",submit);
        res.put("length",contest.getDuration());
        respJson.setSuccess(1);
        respJson.setObj(res);
        return respJson;
    }
}
