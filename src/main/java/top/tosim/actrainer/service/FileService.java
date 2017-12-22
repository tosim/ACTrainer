package top.tosim.actrainer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.dto.PicConfirmData;
import top.tosim.actrainer.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@PropertySource({"classpath:global.properties"})
@Transactional
public class FileService {
    Logger log = LoggerFactory.getLogger(FileService.class);

    @Autowired
    UserDao userDao;

    @Value("${pic_save_dir}")
    String picSaveDirs;

    public  PicConfirmData submitIcon(MultipartFile fileImage, HttpServletRequest request){
        log.info("收到文件");
        if(fileImage == null || request.getSession(true).getAttribute("user") == null){
            if(fileImage == null) log.info("文a件为null");
            if(request.getSession(true).getAttribute("user") == null) log.info("用户未登录");
            return new PicConfirmData();
        }
        PicConfirmData picConfirmData = save(fileImage,request);
        User user = (User)request.getSession(true).getAttribute("user");
        user.setIcon(picConfirmData.getPath());
        userDao.updateIconByPrimaryKey(user.getId(),user.getIcon());
        return picConfirmData;
    }

    public PicConfirmData submitPic(MultipartFile fileImage, HttpServletRequest request){
        log.info("收到文件");
        if(fileImage == null || request.getSession(true).getAttribute("user") == null){
            if(fileImage == null) log.info("文件 = null");
            if(request.getSession(true).getAttribute("user") == null) log.info("用户未登录");
            return new PicConfirmData();
        }
        return save(fileImage,request);
    }

    private PicConfirmData save(MultipartFile fileImage, HttpServletRequest request){
        User user = (User)request.getSession(true).getAttribute("user");
        //为图片改名
        String oldName = "";
        String newName = "";
        String extension = "";
        //图片按照上传时间命名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //存储每张图片的信息
        //获取配置文件中图片的存储路径
        String contextPath = request.getContextPath();
        String realPath = request.getSession().getServletContext().getRealPath("/") + picSaveDirs;  //服务器存储的路径
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+contextPath+"/";   //网上可访问的资源路径

        //依次将图片存储到path路径下
        System.out.println(fileImage.getOriginalFilename());
        oldName = fileImage.getOriginalFilename();
        extension = oldName.substring(oldName.lastIndexOf("."));
        newName = sdf.format(new Date())+extension;
        File target = new File(realPath,newName);
        if(!target.exists()){
            target.mkdirs();
        }
        try {
            fileImage.transferTo(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //记录图片存储信息
        PicConfirmData pic = null;
        try {
            //只存名称，路径已知，从而节省数据库空间
            //pic = new PicConfirmData(URLEncoder.encode(oldName, "utf-8"), path+newName);
            String srcPath = basePath + picSaveDirs + "/" + newName;
            String relativePath = "/" + picSaveDirs + "/" + newName;
            System.out.println(relativePath);
            pic = new PicConfirmData(URLEncoder.encode(oldName, "utf-8"), newName,relativePath);
            //request.getSession(true).setAttribute("iconPath",relativePath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pic;
    }

}
