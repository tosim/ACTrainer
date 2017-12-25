package top.tosim.actrainer.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dao.*;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class ContestService {
    Logger log = LoggerFactory.getLogger(ContestService.class);
    @Autowired
    ContestDao contestDao;
    @Autowired
    UserDao userDao;
    @Autowired
    EditProblemDao editProblemDao;
    @Autowired
    ProblemDao problemDao;
    @Autowired
    SubmissionDao submissionDao;

    public Map<String,Integer> getContestTotalCount(ContestPageSelectDto pageSelectDto){
        Map<String,Integer> totalCount = new HashMap<String, Integer>();
        totalCount.put("totalCount",contestDao.selectTotalCount(pageSelectDto));
        return totalCount;
    }

    public RespJson getContestList(ContestPageSelectDto pageSelectDto){
        RespJson respJson = new RespJson();
        pageSelectDto.validateAndCalculateStart(15);
        List<Contest> ret = contestDao.selectPartByPage(pageSelectDto);
        respJson.setObj(ret);
        respJson.setSuccess(1);
        return respJson;
    }

    public RespJson updateContest(HttpServletRequest request,int id,Contest contest){
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
                editProblemDao.insertSelective(problem);
                contestProblem.setEditedProblemId(problem.getId());
            }
            contestProblem.setIndex(ix);
            contestDao.insertIntoContestProblem(contestProblem);
        }
        respJson.setSuccess(1);
        return respJson;
    }

    public RespJson createContest(HttpServletRequest request, Contest contest){
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
                editProblemDao.insertSelective(problem);
                contestProblem.setEditedProblemId(problem.getId());
            }
            log.info(JSON.toJSONString(contestProblem));
            contestProblem.setIndex(ix);
            contestDao.insertIntoContestProblem(contestProblem);
        }
        respJson.setSuccess(1);
        return respJson;
    }

    public RespJson getContestProblem(HttpServletRequest request,Integer id, String remoteOj, Integer remoteProblemId,String password){
        RespJson respJson = new RespJson();
        Contest contest = contestDao.selectByPrimaryKey(id);
        User user = (User)request.getSession(true).getAttribute("user");
        if(!checkAuthority(user,contest,password)){
            respJson.setSuccess(0);
            return respJson;
        }
        ContestProblem contestProblem = contestDao.selectRow(id,remoteOj,remoteProblemId);
        if(contestProblem.getEditedProblemId() == null){
            respJson.setObj(problemDao.selectByOjAndPid(remoteOj,remoteProblemId+""));
        }else{
            respJson.setObj(editProblemDao.selectByPrimaryKey(contestProblem.getEditedProblemId()));
        }
        respJson.setSuccess(1);
        return respJson;
    }

    public RespJson getContestProblemList(HttpServletRequest request,Integer id,String password){
        RespJson respJson = new RespJson();
        Contest contest = contestDao.selectByPrimaryKey(id);
        User user = (User)request.getSession(true).getAttribute("user");
        if(!checkAuthority(user,contest,password)){
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
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("contest",contest);
        res.put("currentServerTime",new Date().getTime());
        respJson.setSuccess(1);
        respJson.setObj(res);
        return respJson;
    }

    public RespJson getContestRank(int id){
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
        List<ContestProblem> contestProblemList = contestDao.selectRowsByContestId(contest.getId());

        res.put("number",contestProblemList.size());
        respJson.setSuccess(1);
        respJson.setObj(res);
        return respJson;
    }

    private boolean checkAuthority(User user,Contest contest,String password){
        if(user !=  null && user.getId().equals(contest.getUserId())){
            return true;
        }
        if(contest.getStartTime() > new Date().getTime()){
            return false;
        }
        if(contest.getContestType().equals(1)){
            if(contest.getPassword() == null || password == null || !contest.getPassword().equals(password)) {
                if(user == null || !user.getId().equals(contest.getUserId())){
                    return false;
                }
            }
        }
        return true;
    }
}
