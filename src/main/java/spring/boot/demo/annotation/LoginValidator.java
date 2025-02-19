package spring.boot.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/4/13 10:19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginValidator {

    boolean value() default true;

}
