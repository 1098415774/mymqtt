package com.sh.base.page;

import com.sh.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageInterceptor implements HandlerInterceptor {

    @Value("${my.pagesize}")
    private int pagesize;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isNotEmpty(pageNum)){
            PageBean pageBean = new PageBean();
            pageBean.setPageSize(pagesize);
            if (StringUtils.isNotEmpty(pageSize)){
                pageBean.setPageSize(Integer.parseInt(pageSize));
            }
            pageBean.setPageNum(Integer.parseInt(pageNum));
            request.setAttribute("pageBean",pageBean);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
