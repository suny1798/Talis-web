package com.itheima.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAspect {


    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    public void pt(){}

    @Before("execution(public void com.itheima.service.impl.DeptServiceImpl.save(com.itheima.pojo.Dept))")
    public void before(){
        log.info("before方法执行了...");
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around-before方法执行了...");
        Object retVal = pjp.proceed();
        log.info("around-after方法执行了...");
        return retVal;
    }
}
