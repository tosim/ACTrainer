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
    ContestService contestService;
    @Autowired
    ContestDao contestDao;
    @Autowired
    SubmissionDao submissionDao;
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getContestTotalCount(ContestPageSelectDto pageSelectDto){
        return contestService.getContestTotalCount(pageSelectDto);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestList(ContestPageSelectDto pageSelectDto){
        log.info(JSON.toJSONString(pageSelectDto));
        return contestService.getContestList(pageSelectDto);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestProblemList(HttpServletRequest request,@PathVariable("id") Integer id,String password){
        return contestService.getContestProblemList(request,id,password);
    }

    @RequestMapping(value = "/{id}/{remoteOj}/{remoteProblemId}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestProblem(HttpServletRequest request,@PathVariable("id") Integer id,@PathVariable("remoteOj") String remoteOj,@PathVariable("remoteProblemId") Integer remoteProblemId,String password){
        log.info(id + " " + remoteOj + " " + remoteProblemId);
        return contestService.getContestProblem(request,id,remoteOj,remoteProblemId,password);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public RespJson createContest(HttpServletRequest request, @RequestBody Contest contest){
        log.info(JSON.toJSONString(contest));
        return contestService.createContest(request,contest);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @ResponseBody
    public RespJson updateContest(HttpServletRequest request, @PathVariable("id") int id,@RequestBody Contest contest){
        log.info(JSON.toJSONString(contest));
        return contestService.updateContest(request,id,contest);
    }

    @RequestMapping(value = "/{id}/rank",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getContestRank(@PathVariable("id") int id){
        return contestService.getContestRank(id);
    }
}
