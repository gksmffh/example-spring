package com.example.configuration.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.configuration.exception.BaseException;
import com.example.configuration.http.BaseResponseCode;
import com.example.framework.web.bind.annotation.RequestConfig.RequetConfig;

public class BaseHandlerInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle requestURI : {}", request.getRequestURI());
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			logger.info("handlerMethod : {}", handlerMethod);
			RequetConfig requetConfig = handlerMethod.getMethodAnnotation(RequetConfig.class);
			if(requetConfig != null) {
				throw new BaseException(BaseResponseCode.LOGIN_REQUIRED);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle requestURI : {}", request.getRequestURI());
	}

}