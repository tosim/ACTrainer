package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.dto.SolutionPageSelectDto;
import top.tosim.actrainer.entity.Solution;
import top.tosim.actrainer.service.SolutionService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/solutions")
public class SolutionController {
    Logger log = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    SolutionService solutionService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public RespJson createSoulution(HttpServletRequest request,@RequestBody Solution solution){
        return solutionService.createSoulution(request,solution);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getSoulution(@PathVariable("id") int id){
        return solutionService.getSoulution(id);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @ResponseBody
    public RespJson updateSolution(HttpServletRequest request,@RequestBody Solution solution){
        return solutionService.updateSolution(request,solution);
    }


    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getTotalCount(SolutionPageSelectDto pageSelectDto){
        return solutionService.getTotalCount(pageSelectDto);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getListByProblem(SolutionPageSelectDto pageSelectDto){
        return solutionService.getListByProblem(pageSelectDto);
    }
}
