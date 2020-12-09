package com.dev.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
public class ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }

    @GetMapping({"/","index"})
    public String index(Model model){
        model.addAttribute("hello","hello world!");
//        model.addAttribute("hot", Map.of("马老师", "耗子尾汁", "琦玉", "一拳超人"));
//        model.addAttribute("language", List.of("c", "c++", "java", "c#", "python"));
//        model.addAttribute("person", new Person("王大锤", "万万没想到"));
        return "index";
    }

    class Person implements Serializable {
        private String name;
        private String content;

        public Person(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
