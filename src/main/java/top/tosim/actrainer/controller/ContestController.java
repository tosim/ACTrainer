package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dao.ContestDao;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Contest;
import top.tosim.actrainer.entity.ContestProblem;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.service.ContestService;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getContestTotalCount(ContestPageSelectDto pageSelectDto){
        /*Map<String,Integer> totalCount = new HashMap<String, Integer>();
        totalCount.put("totalCount",contestDao.selectTotalCount(pageSelectDto));
        return totalCount;*/
        return contestService.getContestTotalCount(pageSelectDto);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Contest> getContestList(ContestPageSelectDto pageSelectDto){
        log.info(JSON.toJSONString(pageSelectDto));
        /*pageSelectDto.validateAndCalculateStart(15);
        List<Contest> ret = contestDao.selectPartByPage(pageSelectDto);
        log.info(JSON.toJSONString(ret));
        return ret;*/
        return contestService.getContestList(pageSelectDto);
//        return null;
    }
    /*
    * 创建一场比赛，不涉及题目描述的修改
    * {
	        "title":"testjson",
	        "startTime":1511618065170,
	        "duration":360000,
	        "problemList":[["HDU",1000],["POJ",2000]]
        }
    * */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> createContest(HttpServletRequest request, @RequestBody Contest contest){
        /*log.info(JSON.toJSONString(contest));
        User user = (User)request.getSession(true).getAttribute("user");
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(user == null){
            ret.put("success",0);
            return ret;
        }
        contest.setUserId(user.getId());
        contestDao.insert(contest);
        log.info(contest.getId().toString());
        for(List<Object> problem : contest.getProblemList()){
            ContestProblem contestProblem = new ContestProblem();
            contestProblem.setContestId(contest.getId());
            contestProblem.setRemoteOj((String)problem.get(0));
            contestProblem.setRemoteProblemId((Integer)problem.get(1));
            log.info(JSON.toJSONString(contestProblem));
            contestDao.insertIntoContestProblem(contestProblem);
        }
        ret.put("success",1);
        return ret;*/
        return contestService.createContest(request,contest);
    }
    /*
    * 修改比赛的基本信息
    * title
    * startTime
    * duration
    * problemList
    * */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Integer> updateContestProblem(HttpServletRequest request, @PathVariable("id") int id,@RequestBody Contest contest){
        /*log.info(JSON.toJSONString(contest));
        User user = (User)request.getSession(true).getAttribute("user");
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(user == null){
            ret.put("success",0);
            return ret;
        }
        int contestId = id;
        if(contest.getId() == null || id != contest.getId()){
            ret.put("success",0);
            return ret;
        }
        contestDao.updateByPrimaryKeySelective(contest);
        for(List<Object> problem : contest.getProblemList()){
            ContestProblem contestProblem = new ContestProblem();
            contestProblem.setContestId(contestId);
            contestProblem.setRemoteOj((String)problem.get(0));
            contestProblem.setRemoteProblemId((Integer)problem.get(1));
            log.info(JSON.toJSONString(contestProblem));
            contestDao.insertIntoContestProblem(contestProblem);
        }
        ret.put("success",1);
        return ret;*/
        return updateContestProblem(request,id,contest);
    }

    @RequestMapping(value = "/{id}/{remoteOj}/{remoteProblemId}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> modifyProblem(
            HttpServletRequest request,
            @PathVariable("id") int id,
            @PathVariable("remoteOj") String remoteOj,
            @PathVariable("remoteProblemId") String remoteProblemId,
            @RequestBody Problem problem){
        /*log.info("contestId = " + id + " and remoteOj = " + remoteOj + " and remoteProblemId = " + remoteProblemId);
        log.info("put problem info = " + JSON.toJSONString(problem));
        request.getSession(true).setAttribute("user",userDao.selectByPrimaryKey(2));

        User user = (User)request.getSession(true).getAttribute("user");
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(user == null || problem.getRemoteOj() == null || problem.getRemoteProblemId() == null){
            if(user == null) log.info("user is null");
            else log.info("oj or id is null");
            ret.put("success",0);
            return ret;
        }
        if(!problem.getRemoteOj().equals(remoteOj) || !problem.getRemoteProblemId().equals(remoteProblemId)){
            log.info("oj or id not match");
            ret.put("success",0);
            return ret;
        }
        //如果数据库中没有这个比赛，不予与更新
        Contest contest = contestDao.selectByPrimaryKey(id);
        if(contest == null){
            log.info("no such contest");
            ret.put("success",0);
            return ret;
        }
        //如果数据库中这个比赛中没有这道题目，也不予以更新
        if(contestDao.selectProblemFromContestProblem(contest.getId(),remoteOj,remoteProblemId) == 0){
            log.info("no such problem");
            ret.put("success",0);
            return ret;
        }

        log.info("has this contest(id=" + contest.getId() + ") and has this problem " + problem.getRemoteOj() + "," + problem.getRemoteProblemId());
        //数据库中有这个比赛，这个比赛中有这道题目

        //如果这个问题没有自定义编辑过，向edited_problem 插入更新后的问题，并更新contest_problem 对应字段
        Integer editProblemId = contestDao.selectEditIdFromContestProblem(contest.getId(),remoteOj,remoteProblemId);
        if(editProblemId == null){
            log.info("this problem has not edited");
            contestDao.insertEditedProblemSelective(problem);//插入到edited_problem 获取到在edited_problem的id
            contestDao.updateContestProblemForEdit(contest.getId(),remoteOj,remoteProblemId,problem.getId());//更新edited_problem_id字段为上面插入的字段
        }else{
            log.info("this problem has been edited");
            problem.setId(editProblemId);
            contestDao.updateEditedProblemByPrimaryKey(problem);
        }
        ret.put("success",1);
        return ret;*/
        return contestService.modifyProblem(request,id,remoteOj,remoteProblemId,problem);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Contest getContestProblemList(@PathVariable("id") Integer id){
        return contestDao.selectByPrimaryKey(id);
    }
}
