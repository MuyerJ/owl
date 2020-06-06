package com.owl.controller;

import com.owl.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/param")
public class ParamBindController {

    @RequestMapping(value = "/page")
    public String page(){
        return "param";
    }

    @RequestMapping(value = "/stringAndInteger")
    public String stringAndInteger(String param1,int param2){
        System.out.println(param1);
        System.out.println(param2);
        return "success";
    }

    @RequestMapping(value = "/getObject")
    public String getObject(User user){
        System.out.println("get:"+user);
        return "success";
    }

    @RequestMapping(value = "/postObject",method = RequestMethod.POST)
    public String postObject(User user){
        System.out.println("post:"+user);
        return "success";
    }

    @RequestMapping(value = "/postCollection",method = RequestMethod.POST)
    public String postCollection(User user){
        System.out.println("post:"+user);
        return "success";
    }
}
