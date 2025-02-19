package spring.boot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot启动类
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/02/27 20:35
 */
@MapperScan(basePackages = "spring.boot.demo.dao")
@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

}
