package com.owl.controller;

import com.owl.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/converter")
public class ConverterController {

    @RequestMapping(value = "/page")
    public String page() {
        return "converter";
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String converterDate(Date date) {
        System.out.println(date);
        return "success";
    }

    @RequestMapping(value = "/dateString", method = RequestMethod.POST)
    public String converterDate1(String date) {
        System.out.println(date);
        return "success";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String convertUser(User user) {
        System.out.println(user);
        return "success";
    }
}
