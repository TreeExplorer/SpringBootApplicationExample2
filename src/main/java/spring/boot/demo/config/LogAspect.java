package spring.boot.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/3 1:03
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Before("within(spring.boot.demo.controller.*)")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("类：{}, 接收请求的方法：{}()：请求参数：{}", method.getDeclaringClass().getName(), method.getName(), args);
    }

    @AfterReturning(value = "within(spring.boot.demo.controller.*)",returning = "rvt")
    public void after(JoinPoint joinPoint, Object rvt) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("类：{}, 返回响应的方法：{}()：响应参数：{}", method.getDeclaringClass().getName(), method.getName(), rvt);
    }
}
