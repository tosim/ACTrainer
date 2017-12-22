package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.service.SubmissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/submissions")
public class SubmissionController {
    Logger log = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionService submissionService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getSubmissionList(SubmissionPageSelectDto pageSelectDto){
        return submissionService.getSubmissionList(pageSelectDto);
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getSubmissionTotalCount(SubmissionPageSelectDto pageSelectDto){
        return submissionService.getSubmissionTotalCount(pageSelectDto);
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> doSubmit(HttpServletRequest request, @RequestBody Submission submission){
        return submissionService.doSubmit(request,submission);
    }
}
