package com.itheima.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RecordTimeAspect {


    @Around("execution(* com.itheima.service.impl.*.*(..))")
    public  Object recordTime(ProceedingJoinPoint pjp) throws Throwable {

        // 获取方法执行前的时间
        long begin = System.currentTimeMillis();

        // 执行目标方法
        Object proceed = pjp.proceed();

        // 获取方法执行后的时间
        long end = System.currentTimeMillis();
        log.info("方法{} 执行耗时：{}", pjp.getSignature().getName(), end-begin);

        return proceed;
    }

}
