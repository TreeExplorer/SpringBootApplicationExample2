package spring.boot.demo.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2023-03-21 17:35
 */
@Slf4j
@Component
public class HttpApiThreadPool {

    /**
     * 获取当前系统的CPU 数目
     */
    static int cpuNums = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池的最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = cpuNums * 5;

    public static ExecutorService httpApiThreadPool;

    static{
        // 线程池核心池的大小
        int corePoolSize = 10;
        log.info("创建线程数:" + corePoolSize + ",最大线程数:" + MAXIMUM_POOL_SIZE);
        // 建立10个核心线程，线程请求个数超过20，则进入队列等待
        httpApiThreadPool = new ThreadPoolExecutor(corePoolSize, MAXIMUM_POOL_SIZE, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100),new ThreadFactoryBuilder().setNameFormat("PROS-%d").build());
    }
}
