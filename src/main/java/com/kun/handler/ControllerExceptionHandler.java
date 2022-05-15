package com.kun.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
 * Created by Daniel on 2022/5/15
 * */
@ControllerAdvice  //这个注解会拦截所有Controller这个注解的类，但是下面的ExceptionHandler标注了只会拦截Exception级别的异常并作处理
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @ExceptionHandler(Exception.class) //标注这个方法是用来做异常处理的,参数代表拦截的信息是Excepttion级别的
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //使用logger记录错误信息
        logger.error("Request URL:{},Exception:{}",request.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
