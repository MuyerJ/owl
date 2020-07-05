/**
 * @author: wmj
 * @date: 2020年06月26日 12时45分
 * @Copyright
 */
package com.owl.controller;

import com.owl.adapter.SelfController;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @classname：TestController
 * @Description：测试自定义的controller
 * @date:2020/6/26 12:45 PM
 * @author：wmj
 * @version：V1.0
 */
@Controller("/selfTest")
public class TestController implements SelfController {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("测试自定义的controller适配器！！");
        return null;
    }
}