package com.owl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 常用注解测试
 *  RequestParam:
 * 	RequestBody:获取post的请求体，get请求方式不适用
 * 	PathVariable:
 *        @RequestMapping:
 * 		建立url和请求方法之间的关系
 * 		可修饰方法和类
 * 		属性：value，method(可传入数组)，params(指定限制传入参数的条件)
 * 	ModelAttribute:
 * 		修饰方法和参数
 * 		放方法上优先执行：两种写法，有返回值；没有使用map
 * 	SessionAttribute:用于多次执行控制方法键的参数共享
 * 		jsp中加入：isELIgored="false"==>${requestScope},Model,${sessionScope}
 * 	CookieValue:
 *
 *
 */

@Controller
@RequestMapping(value = "/annotation")
public class MvcAnnotationController {

    @RequestMapping(value = "page")
    public String page() {
        return "annotation";
    }

    /**
     * 测试: RequestParam
     */

    /**
     * SpringMVC取参数有三种形式：
     *   @RequestParam注解到参数上 ：
     *      请求没有参数param1,会报错；可以通过required设置会false来解决这个报错问题(这个第二种没啥区别)
     *  不写注解直接名字 ：
     *      请求没有参数param2不会报错，但会赋值为空
     *   通过request.getParameter("")来获取
     * @return
     */
    @RequestMapping(value = "/requestParam" ,method = RequestMethod.POST)
    public String testRequestParam(@RequestParam(required = false) String param1, String param2, HttpServletRequest request){
        System.out.println(param1);
        System.out.println(param2);
        String param3 = request.getParameter("param3");
        System.out.println(param3);
        return "success";
    }
}
