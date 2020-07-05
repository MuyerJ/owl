/**
 * @author: wmj
 * @date: 2020年06月26日 12时40分
 * @Copyright
 */
package com.owl.adapter;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @classname：SelfController
 * @Description：自定义一个controller接口
 * @date:2020/6/26 12:40 PM
 * @author：wmj
 * @version：V1.0
 */
public interface SelfController {

    //用来处理请求
    @Nullable
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}