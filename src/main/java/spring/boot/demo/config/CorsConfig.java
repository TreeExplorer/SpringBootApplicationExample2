package spring.boot.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description: 解决前后端分离的跨域问题
 * @date 2022/3/23 15:44
 */
@Configuration
public class CorsConfig {

    /**
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 注册CORS过滤器
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        // 是否支持安全证书
        config.setAllowCredentials(true);
        // 允许任何域名使用
        config.addAllowedOrigin("*");
        // 允许任何头
        config.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        config.addAllowedMethod("*");
        // 预检请求的有效期，单位为秒。
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
