package com.kun.web;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;




/*
* Created by Daniel on 2022/5/15
* */

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
//        int i=9/0;
//        String blog = null;
//        if(blog == null){
//            throw  new NotFoundException("博客不存在");
//        }
        System.out.println("---------index------------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        return "admin/blogs-input";
    }

}
