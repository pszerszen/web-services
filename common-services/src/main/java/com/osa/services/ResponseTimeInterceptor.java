package com.osa.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ResponseTimeInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        log.debug("Calling {} method by URL:[{}] {}",
                getCalledMethod((HandlerMethod) handler),
                request.getMethod(),
                request.getRequestURL().toString());

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        //log it
        log.debug("Completed {} method by URL:[{}] {} with execution time: {} ms",
                getCalledMethod((HandlerMethod) handler),
                request.getMethod(),
                request.getRequestURL().toString(),
                executeTime);
    }

    private static String getCalledMethod(final HandlerMethod handler) {
        return String.join("::", handler.getBean().getClass().getSimpleName(), handler.getMethod().getName());
    }
}
