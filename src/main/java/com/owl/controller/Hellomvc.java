package com.owl.controller;


import com.owl.dao.UserDao;
import com.owl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hellomvc {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/hellomvc")
    public String hello(){
        User user = userDao.getUserById("1");
        System.out.println(user);
        return "success";
    }
}
