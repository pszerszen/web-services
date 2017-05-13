package com.osa.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ResponseTimeInterceptor extends HandlerInterceptorAdapter implements EndpointInterceptor {

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

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        MethodEndpoint methodEndpoint = (MethodEndpoint) endpoint;

        log.debug("Calling {} soap method.", methodEndpoint.getMethod().getName());

        long startTime = System.currentTimeMillis();
        messageContext.setProperty(START_TIME, startTime);

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        MethodEndpoint methodEndpoint = (MethodEndpoint) endpoint;

        long startTime = (Long) messageContext.getProperty(START_TIME);
        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        //log it
        log.debug("Completed {} method with execution time: {} ms",
                methodEndpoint.getMethod().getName(),
                executeTime);
    }

    private static String getCalledMethod(final HandlerMethod handler) {
        return String.join("::", handler.getBean().getClass().getSimpleName(), handler.getMethod().getName());
    }
}
