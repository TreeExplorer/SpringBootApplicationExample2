package spring.boot.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.boot.demo.domain.SysNotice;
import spring.boot.demo.service.SysNoticeService;
import spring.boot.demo.service.SysUserService;

import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/4/11 12:23
 */
@Component
@EnableScheduling
@Slf4j
public class ExampleTask {

    @Autowired
    SysNoticeService sysNoticeService;

    /**
     * 每天 00:00 点执行
     * 使用 corn 表达式，确定定时任务什么时候执行
     */
    @Scheduled(cron="5 * * * * ?")
    public void exampleTask() {
        try {
            log.info("定时任务启动");
            List<SysNotice> aaa = sysNoticeService.aaa();

            log.info("--这是定时任务的执行内容--" + aaa.toString());
            log.info("定时任务结束");
        } catch (Exception e) {
            log.error("定时任务异常：" + e);
        }
    }
}
