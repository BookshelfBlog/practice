package com.dev.aop.controller;

import com.dev.aop.annotation.Log;
import com.dev.aop.aspect.LogAspect;
import com.dev.aop.util.ServletUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description :  测试 //描述
 */
@RestController
public class IndexController {

    @Log(title = "测试Post", type = Log.BusinessType.INSERT, isParameter = true)
    @PostMapping("/Post")
    public String indexPost(@RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        ServletUtil.getSession().setAttribute("user",new LogAspect().new User(1,"admin","技术部"));
        return "success";
    }

    @Log(title = "测试Get", type = Log.BusinessType.OTHER, isParameter = true)
    @GetMapping("/Get")
    public String indexGet(Map<String, Object> map) {
        System.out.println(map.toString());
        ServletUtil.getSession().setAttribute("user",new LogAspect().new User(1,"admin","技术部"));
        return "success";
    }

    @Log(title = "测试err", type = Log.BusinessType.OTHER, isParameter = true)
    @PostMapping("/err")
    public String indexErr(@RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        ServletUtil.getSession().setAttribute("user",new LogAspect().new User(1,"admin","技术部"));
        throw new RuntimeException("NullPointException");
    }
}
