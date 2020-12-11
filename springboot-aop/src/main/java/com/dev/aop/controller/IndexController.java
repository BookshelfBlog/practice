package com.dev.aop.controller;

import com.dev.aop.annotation.Log;
import com.dev.aop.aspect.LogAspect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName : IndexController  //类名
 * @Description :   //描述
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Log(title = "测试新增", type = Log.BusinessType.INSERT, isParameter = true)
    @PostMapping("/insert")
    @ResponseBody
    public String indexInsert(@RequestBody Map<String, Object> map) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        request.getSession().setAttribute("user",new LogAspect().new User(1,"admin","技术部"));
        System.out.println(request.getSession().getAttribute("user"));
        return "success";
    }
}
