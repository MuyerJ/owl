package com.owl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ServletAPIController {

    @RequestMapping(value = "/servlet")
    public String getServlet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request);
        //获取请求的参数
        request.getParameter("");
        System.out.println(response);
        return "success";
    }
}
