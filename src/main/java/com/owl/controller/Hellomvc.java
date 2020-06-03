package com.owl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hellomvc {

    @RequestMapping(value = "/hellomvc")
    public String hello(){
        return "success";
    }
}
