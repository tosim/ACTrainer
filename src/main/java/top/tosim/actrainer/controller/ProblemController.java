package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.config.init.SubmissionManager;
import top.tosim.actrainer.dao.ProblemDao;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.remote.provider.hdu.HDUCrawler;
import top.tosim.actrainer.service.ProblemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/problems")
public class ProblemController {
    private Logger log = LoggerFactory.getLogger(ProblemController.class);
//    @Autowired
//    private ProblemDao problemDao;
    @Autowired
    private ProblemService problemService;
    /*
    * 获取题目详情
    *   remoteOj
    *   remoteProblemId
    * */
    @RequestMapping(value = "/{remoteOj}/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Problem getProblem(@PathVariable String remoteOj,@PathVariable String id){
        return problemService.getProblem(remoteOj,id);
        /*System.out.println("pid = " + id);
//        Problem problem = HDUCrawler.crawl(id);
        Problem problem = problemDao.selectByOjAndPid(RemoteOJ.HDU.name(),id);
        log.info(JSON.toJSONString(problem));
        return problem;*/
    }

    /*
    * 带筛选参数的分页查询
    * type:url params
    *   page
    *   size
    *   remoteOj
    *   remoteProblemId
    *   title
    * */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getProblemList(ProblemPageSelectDto pageSelectDto){
        /*pageSelectDto.validateAndCalculateStart(10);
//        log.info(JSON.toJSONString(pageSelectDto));
        List<Map<String,Object>> ret = problemDao.selectPartByPage(pageSelectDto);
        log.info(JSON.toJSONString(ret));
        return ret;*/
        return problemService.getProblemList(pageSelectDto);
    }

    /*
    * 获取带参数的分页查询的总数
    * type:url params
    *   remoteOj
    *   remoteProblemId
    *   title
    * */
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getProblemTotalCount(ProblemPageSelectDto pageSelectDto){
        /*Map<String,Integer> totalCount = new HashMap<String, Integer>();
        totalCount.put("totalCount",problemDao.selectTotalCount(pageSelectDto));
        return totalCount;*/
        return problemService.getProblemTotalCount(pageSelectDto);
    }
}
