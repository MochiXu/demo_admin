package com.mmcc.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FormController {
    @GetMapping("/form_layouts")
    public String get_layouts(){
        System.out.println("访问form_layouts");
        return "form/form_layouts";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg")MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {

        System.out.println("uploading..."+email+"--"+username+"--"+headerImg);
        //保存到文件服务器
        if(!headerImg.isEmpty()){
            //保存到文件服务器，OSS云存储
            String originalFilename=headerImg.getOriginalFilename();
            System.out.println("文件原名称:"+originalFilename);
            headerImg.transferTo(new File("E:\\Java\\"+username+"_"+originalFilename));
            System.out.println("单个文件保存成功");
        }
        //保存多个文件到服务器
        if(photos.length>0){
            for (MultipartFile photo:photos){
                if(!photo.isEmpty()){
                    String origin=photo.getOriginalFilename();
                    photo.transferTo(new File("E:\\Java\\"+origin));
                }
            }
            System.out.println("多个文件保存成功");
        }


        //假设上传完文件后刷新当前页面
        return "main";
    }
}
