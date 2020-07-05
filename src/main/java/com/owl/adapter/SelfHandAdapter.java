/**
 * @author: wmj
 * @date: 2020年06月26日 12时39分
 * @Copyright
 */
package com.owl.adapter;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import util.crawl.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Handler;

/**
 * @classname：SelfHandAdapter
 * @Description：自定义一个适配器
 * @date:2020/6/26 12:39 PM
 * @author：wmj
 * @version：V1.0
 */
public class SelfHandAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof SelfController;
    }

    @Override
    public ModelAndView handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle)
            throws Exception {
        return ((SelfController)handle).handleRequest(httpServletRequest,httpServletResponse);
    }

    @Override
    public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
        return -1;
    }
}