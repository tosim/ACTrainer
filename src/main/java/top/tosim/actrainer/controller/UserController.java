package top.tosim.actrainer.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.tosim.actrainer.dto.RespJson;
import top.tosim.actrainer.dto.UserPageSelectDto;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> getUserTotalCount(UserPageSelectDto pageSelectDto){
        return userService.getUserTotalCount(pageSelectDto);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getUserRankList(UserPageSelectDto pageSelectDto){
        return userService.getUserRankList(pageSelectDto);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,User> getUserById(HttpServletRequest request,@PathVariable int id){
        return userService.getUserById(request,id);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @ResponseBody
    public RespJson updateUserById(HttpServletRequest request, @PathVariable("id") int id, @RequestBody User putUser){
        log.info("resive put user id = " + id);
        log.info(JSON.toJSONString(putUser));
        return userService.updateUserById(request,id,putUser);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> regist(@RequestBody User postUser){
        return userService.regist(postUser);
    }
}
