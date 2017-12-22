package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.service.ExcelService;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value="/excels")
public class ExcelsController {
    Logger log = LoggerFactory.getLogger(ExcelsController.class);
    @Autowired
    ExcelService excelService;

    @RequestMapping(value = "/contests/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson downloadContestRank(HttpServletResponse response,@PathVariable("id") int contestId){
        return excelService.downloadContestRank(response,contestId);
    }

}
