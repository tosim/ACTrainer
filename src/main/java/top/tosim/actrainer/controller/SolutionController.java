package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dao.SolutionDao;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.dto.SolutionPageSelectDto;
import top.tosim.actrainer.entity.Solution;
import top.tosim.actrainer.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/solutions")
public class SolutionController {
    Logger log = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    SolutionDao solutionDao;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public RespJson createSoulution(HttpServletRequest request,@RequestBody Solution solution){
        solution.setCreateTime(new Date());
        log.info(JSON.toJSONString(solution));
        RespJson respJson = new RespJson();
        User user = (User)request.getSession(true).getAttribute("user");
        if(user == null){
            respJson.setSuccess(0);
            respJson.setMsg("not login");
            return respJson;
        }
        solution.setUserId(user.getId());
        solutionDao.insertSelective(solution);
        respJson.setSuccess(1);
        return respJson;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getSoulution(@PathVariable("id") int id){
        RespJson respJson = new RespJson();
        Solution solution = solutionDao.selectByPrimaryKey(id);
        respJson.setSuccess(1);
        respJson.setObj(solution);
        return respJson;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @ResponseBody
    public RespJson updateSolution(HttpServletRequest request,@RequestBody Solution solution){
        RespJson respJson = new RespJson();
        User user = (User)request.getSession(true).getAttribute("user");
        if(user == null || solution.getId() == null){
            respJson.setSuccess(0);
            if(user == null) respJson.setMsg("not login");
            else respJson.setMsg("don't have solution id");
            return respJson;
        }else if(!solution.getUserId().equals(user.getId())){
            respJson.setSuccess(0);
            respJson.setMsg("you don't own this solution");
            return respJson;
        }
        solutionDao.updateByPrimaryKey(solution);
        respJson.setSuccess(1);
        return respJson;
    }


    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getTotalCount(SolutionPageSelectDto pageSelectDto){
        RespJson respJson = new RespJson();
        pageSelectDto.validateAndCalculateStart(10);
        Map<String,Integer> res = new HashMap<String,Integer>();
        res.put("totalCount",solutionDao.selectTotalCount(pageSelectDto));
        respJson.setSuccess(1);
        respJson.setObj(res);
        return respJson;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public RespJson getListByProblem(SolutionPageSelectDto pageSelectDto){
        RespJson respJson = new RespJson();
        pageSelectDto.validateAndCalculateStart(10);
        respJson.setSuccess(1);
        respJson.setObj(solutionDao.selectPartByPage(pageSelectDto));
        return respJson;
    }
}
