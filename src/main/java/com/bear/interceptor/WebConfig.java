package com.bear.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by bear on 2020/3/15.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // admin/后面的访问路径全部拦截
                .addPathPatterns("/admin/**")
                // 排查admin路径， 不然登录也被拦截了！
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
