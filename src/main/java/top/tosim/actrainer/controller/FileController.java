package top.tosim.actrainer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.tosim.actrainer.dto.PicConfirmData;
import top.tosim.actrainer.service.FileService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/files")
public class FileController {
    Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value="/icon",method = RequestMethod.POST)
    @ResponseBody
    public PicConfirmData submitIcon(@RequestParam(value = "icon",required = false) MultipartFile fileImage, HttpServletRequest request){
        return submitIcon(fileImage,request);
    }

    @RequestMapping(value="/pic",method = RequestMethod.POST)
    @ResponseBody
    public PicConfirmData submitPic(@RequestParam(value = "pic",required = false) MultipartFile fileImage, HttpServletRequest request){
        return submitPic(fileImage,request);
    }
}


