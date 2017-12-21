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
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    Logger log = LoggerFactory.getLogger(SessionController.class);
//    @Autowired
//    UserDao userDao;

    @Autowired
    SessionService sessionService;
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> checkExpire(HttpServletRequest request){
        Map<String,Integer> ret = new HashMap<String,Integer>();
        log.info(JSON.toJSONString(request.getSession(true).getAttribute("user")));
        if(request.getSession(true).getAttribute("user") == null) ret.put("success",0);
        else ret.put("success",1);
        return ret;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,User> login(HttpServletRequest request, @RequestBody User requestUser){
        return sessionService.login(request,requestUser);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> logout(HttpServletRequest request){
        return sessionService.logout(request);
    }
}
