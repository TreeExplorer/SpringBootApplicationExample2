package spring.boot.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2020/9/28 22:40
 */
@Slf4j
@Aspect
@Component
public class TransactionAspect {

    @Around("execution(* spring.boot.demo.service.impl.*.insert*(..))" +
            "|| execution(* spring.boot.demo.service.impl.*.update*(..))" +
            "|| execution(* spring.boot.demo.service.impl.*.delete*(..))"
    )
    @Transactional(rollbackFor=Exception.class)
    public Object transactionAdvice(ProceedingJoinPoint joinPoint) {
        TransactionStatus status = dstManager.getTransaction(def);
        log.info("-------开启事务");
        Object object;
        try {
            object = joinPoint.proceed();
            log.info("-------提交事务");
            dstManager.commit(status);
        } catch (Throwable throwable) {
            object = throwable;
            log.info("-------回滚");
            log.error("错误信息", throwable);
            dstManager.rollback(status);
        }
        return object;
    }

    @Autowired
    private DataSourceTransactionManager dstManager;

    @Autowired
    private DefaultTransactionDefinition def;

}
