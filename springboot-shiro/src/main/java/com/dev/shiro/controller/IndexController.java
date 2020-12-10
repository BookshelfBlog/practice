package com.dev.shiro.controller;

import com.dev.shiro.entity.Login;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : IndexController  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2020-12-10 10:18  //时间
 */
@Controller
public class IndexController {

    @GetMapping({"/","/login"})
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login_ajax(HttpServletRequest request, Login login){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(login.getLoginName(),login.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            request.getSession().setAttribute("user", login);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","index");
        model.addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
        return "index";
    }

    @GetMapping("/mobile")
    public String mobile(Model model){
        model.addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
        model.addAttribute("msg", "mobile");
        return "index";
    }

    @GetMapping("/platform")
    public String platform(Model model){
        model.addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
        model.addAttribute("msg", "platform");
        return "index";
    }

    @GetMapping("/system")
    public String system(Model model){
        model.addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
        model.addAttribute("msg", "system");
        return "index";
    }

    @GetMapping("/unauth")
    public String unauth(Model model){
        model.addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
        model.addAttribute("msg", "Authority authentication failed!");
        return "unauth";
    }
}
