package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tosim.actrainer.config.init.SubmissionManager;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.service.SubmissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/submissions")
public class SubmissionController {
    Logger log = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionDao submissionDao;

    @Autowired
    SubmissionService submissionService;
    /*
    * 带参数的分页查询
    * fromId
    * remoteOj
    * remoteProblemId
    * accountName
    * language
    * status
    * */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String testSubmit(Submission submission){
//        submissionDao.insertSelective(submission);
//        log.info(JSON.toJSONString(submission));
//        SubmissionManager.putSubmission(RemoteOJ.valueOf(submission.getRemoteOj()),submission);
        return "submit_page";
    }

    @RequestMapping(value = "/test/sub",method = RequestMethod.POST)
    public String testDoSubmit(Submission submission){
        submission.setUserId(1);
        submission.setAccountName("Fireman");
        submission.init();
        submissionDao.insertSelective(submission);
        log.info(JSON.toJSONString(submission));
        SubmissionManager.putSubmission(RemoteOJ.valueOf(submission.getRemoteOj()),submission);
        return "submit_page";
    }
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getSubmissionList(SubmissionPageSelectDto pageSelectDto){
        /*pageSelectDto.validateAndCalculateStart(15);
        List<Map<String,Object>> ret = submissionDao.selectPartByPage(pageSelectDto);
        return ret;*/
        return submissionService.getSubmissionList(pageSelectDto);
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getSubmissionTotalCount(SubmissionPageSelectDto pageSelectDto){
        /*Map<String,Integer> totalCount = new HashMap<String, Integer>();
        totalCount.put("totalCount",submissionDao.selectTotalCount(pageSelectDto));
        return totalCount;*/
        return submissionService.getSubmissionTotalCount(pageSelectDto);
    }

    /*
    *处理提交
    * */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> doSubmit(HttpServletRequest request, @RequestBody Submission submission){
        /*User user = (User)request.getSession(false).getAttribute("user");
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(user == null){
            ret.put("success",0);
            return ret;
        }
        submission.setUserId(user.getId());
        submission.setAccountName(user.getAccountName());
        submission.init();//init languageCode and submitTime and status
        log.info(JSON.toJSONString(submission));
        int insertNum = submissionDao.insert(submission);
        log.info("insertNum = " + insertNum);
        log.info("submissionId = " + submission.getId());
        SubmissionManager.putSubmission(RemoteOJ.HDU,submission); //提交到提交队列等待提交
        ret.put("success",1);
        return ret;*/
        return submissionService.doSubmit(request,submission);
    }
}
