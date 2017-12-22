package top.tosim.actrainer.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class SessionService {
    Logger log = LoggerFactory.getLogger(SessionService.class);
    @Autowired
    UserDao userDao;

    public Map<String,Integer> checkExpire(HttpServletRequest request){
        Map<String,Integer> ret = new HashMap<String,Integer>();
        log.info(JSON.toJSONString(request.getSession(true).getAttribute("user")));
        if(request.getSession(true).getAttribute("user") == null) ret.put("success",0);
        else ret.put("success",1);
        return ret;
    }

    public Map<String,User> login(HttpServletRequest request,User requestUser){
        log.info(JSON.toJSONString(requestUser));
        log.info("accountNam = " + requestUser.getAccountName() + " and password = " + requestUser.getPassword());
        Map<String,User> ret = new HashMap<String, User>();
        HttpSession session = request.getSession(true);
        if(session.getAttribute("user") != null){
            ret.put("user",(User)session.getAttribute("user"));
            return ret;
        }
        User user = userDao.selectByAccountNameAndPass(requestUser.getAccountName(),requestUser.getPassword());
        if(user != null){
            user.setPassword(null);
            ret.put("user",user);
            request.getSession(true).setAttribute("user",user);
        }else{
            ret.put("user",null);
        }
        return ret;
    }

    public Map<String,Integer> logout(HttpServletRequest request){
        log.info("get delete request");
        Map<String,Integer> ret = new HashMap<String, Integer>();
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");
        ret.put("success",1);
        return ret;
    }
}
