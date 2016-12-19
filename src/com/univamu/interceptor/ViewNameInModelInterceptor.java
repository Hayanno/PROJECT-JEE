package com.univamu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInModelInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Inject name view inside each model so it can be retrieved from .jsp file
	 */
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.addObject("springViewName", modelAndView.getViewName());
        }
        
        super.postHandle(request, response, handler, modelAndView);
    }
	
}
