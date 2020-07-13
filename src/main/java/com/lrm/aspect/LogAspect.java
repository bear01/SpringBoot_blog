package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by bear on 2020/3/13.
 * AspectJ 切面
 * @Before: 前置通知, 在方法执行之前执行
 * @After: 后置通知, 在方法执行之后执行 。
 * @AfterRunning: 返回通知, 在方法返回结果之后执行
 * @AfterThrowing: 异常通知, 在方法抛出异常之后
 * @Around: 环绕通知, 围绕着方法执行
 */
@Aspect
@Component
public class LogAspect {
    // 使用当前运行类初始化日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码.
    使用 @Pointcut 来声明切入点表达式.
    后面的其他通知直接使用方法名来引用当前的切入点表达式. */
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log() {}


    @Before("log()")   // 1. 前置通知, 在方法执行之前执行
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")   // 2. 后置通知, 在方法执行之后执行 。不管是抛出异常或者正常退出都会执行。
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")   // 3.返回通知, 在方法返回结果之后执行（方法正常退出时执行）
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
