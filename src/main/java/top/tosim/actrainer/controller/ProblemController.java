package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.service.ProblemService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/problems")
public class ProblemController {
    private Logger log = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value = "/{remoteOj}/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Problem getProblem(@PathVariable String remoteOj,@PathVariable String id){
        return problemService.getProblem(remoteOj,id);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getProblemList(ProblemPageSelectDto pageSelectDto){
        return problemService.getProblemList(pageSelectDto);
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getProblemTotalCount(ProblemPageSelectDto pageSelectDto){
        return problemService.getProblemTotalCount(pageSelectDto);
    }
}
