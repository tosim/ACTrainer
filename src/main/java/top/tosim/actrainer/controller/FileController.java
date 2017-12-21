package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.dto.PicConfirmData;
import top.tosim.actrainer.entity.User;
import top.tosim.actrainer.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {
    Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;

    @RequestMapping(value="/icon",method = RequestMethod.POST)
    @ResponseBody
    public PicConfirmData submitIcon(@RequestParam(value = "icon",required = false) MultipartFile fileImage, HttpServletRequest request){
        log.info("收到文件");
        if(fileImage == null || request.getSession(true).getAttribute("user") == null){
            if(fileImage == null) log.info("文a件为null");
            if(request.getSession(true).getAttribute("user") == null) log.info("用户未登录");
            return new PicConfirmData();
        }
        PicConfirmData picConfirmData = fileService.save(fileImage,request);
        User user = (User)request.getSession(true).getAttribute("user");
        user.setIcon(picConfirmData.getPath());
        userDao.updateIconByPrimaryKey(user.getId(),user.getIcon());
        return picConfirmData;
    }

    @RequestMapping(value="/pic",method = RequestMethod.POST)
    @ResponseBody
    public PicConfirmData submitPic(@RequestParam(value = "pic",required = false) MultipartFile fileImage, HttpServletRequest request){
        log.info("收到文件");
        if(fileImage == null || request.getSession(true).getAttribute("user") == null){
            if(fileImage == null) log.info("文件 = null");
            if(request.getSession(true).getAttribute("user") == null) log.info("用户未登录");
            return new PicConfirmData();
        }
        return fileService.save(fileImage,request);
    }


}


