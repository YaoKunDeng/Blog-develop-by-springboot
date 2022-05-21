package com.kun.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//1. 记录日志内容【采用aop方式】
//        1. 请求rul
//        2. 访问者ip
//        3. 调用方法
//        4. 参数args
//        5. 返回内容




@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.kun.web..*.*(..))")
    public void log(){

    }

    @Before("log()")   //表示这个方法在切面之前执行
    public void doBefore(JoinPoint joinPoint){ //JoinPoint切面对象
        //拿到request,用request去获取需要的东西
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url ,ip, classMethod, args);
        logger.info("Request:{}",requestLog);
        System.out.println("切面中的Before方法");

    }
    @After("log()")
    public void doAfter(){
//        logger.info("--------------Aftere--------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }

//    内部类，目的是将需要用到的参数封装
    private class RequestLog{
        private String url;  //请求的rul
        private String ip;  //访问者ip
        private String className;   //调用方法
        private Object[] args;  //参数args

    public RequestLog(String url, String ip, String className, Object[] args) {
        this.url = url;
        this.ip = ip;
        this.className = className;
        this.args = args;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", className='" + className + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
}


