package com.owl.controller;

import com.owl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable("id") String userId,ModelAndView mv){
        mv.setViewName("user");
        mv.addObject("user",userService.getUserById(userId));
        return mv;
    }
}
