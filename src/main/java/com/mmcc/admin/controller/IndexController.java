package com.mmcc.admin.controller;

import com.mmcc.admin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    /**
     * 访问登录页
     * */
    @GetMapping(value={"/","/login"})
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession httpSession, Model model){
        if(!StringUtils.isEmpty(user.getUserName())&&!StringUtils.isEmpty(user.getPassword())){
            //只要是有值，那就视为登陆成功
            httpSession.setAttribute("loginUser",user);
            //登陆成功，重定向到main.html,能够防止表单重复提交
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg","请输入账号密码");
            //回到登陆页面
            return "login";
        }

    }

//    为了解决重复提交问题，但是未登录情况下不能直接对此页面请求
    @GetMapping("/main.html")
    public String mainPage(HttpSession  session,Model model){
        //判断用户是否已经登录，可使用拦截器与过滤器机制
        Object loginUser=session.getAttribute("loginUser");//从session中获取loginUser字段
        if(loginUser==null) {
            //回到登陆页面
            model.addAttribute("msg","服务器Session失效");
            return "login";
        }
        else
            return "main";
    }
}
