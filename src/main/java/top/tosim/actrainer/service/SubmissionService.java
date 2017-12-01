package top.tosim.actrainer.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tosim.actrainer.config.init.LanguageMapManager;
import top.tosim.actrainer.config.init.SubmissionManager;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.remote.RemoteOJ;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionService {
    Logger log = LoggerFactory.getLogger(SubmissionService.class);
    @Autowired
    SubmissionDao submissionDao;

    public List<Map<String,Object>> getSubmissionList(SubmissionPageSelectDto pageSelectDto){
        pageSelectDto.validateAndCalculateStart(15);
        List<Map<String,Object>> ret = submissionDao.selectPartByPage(pageSelectDto);
        return ret;
    }

    public Map<String,Integer> getSubmissionTotalCount(SubmissionPageSelectDto pageSelectDto){
        Map<String,Integer> totalCount = new HashMap<String, Integer>();
        log.info(JSON.toJSONString(pageSelectDto));
        pageSelectDto.validateAndCalculateStart(15);
        totalCount.put("totalCount",submissionDao.selectTotalCount(pageSelectDto));
        return totalCount;
    }

    /*
    *处理提交
    * */
    public Map<String,Integer> doSubmit(HttpServletRequest request,Submission submission){
        User user = (User)request.getSession(false).getAttribute("user");
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(user == null){
            ret.put("success",0);
            return ret;
        }
        submission.setLanguageCode(LanguageMapManager.getLanguageMap(RemoteOJ.valueOf(submission.getRemoteOj())).get(submission.getLanguage()));
        log.info("language code = " + submission.getLanguageCode());
        submission.setUserId(user.getId());
        submission.setAccountName(user.getAccountName());
        submission.init();//init languageCode and submitTime and status
        log.info(JSON.toJSONString(submission));
        int insertNum = submissionDao.insert(submission);
        log.info("insertNum = " + insertNum);
        log.info("submissionId = " + submission.getId());
        SubmissionManager.putSubmission(RemoteOJ.HDU,submission); //提交到提交队列等待提交
        ret.put("success",1);
        return ret;
    }
}
