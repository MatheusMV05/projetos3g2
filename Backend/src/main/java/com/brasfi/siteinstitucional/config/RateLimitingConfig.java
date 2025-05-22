package com.brasfi.siteinstitucional.config;

import com.brasfi.siteinstitucional.security.RateLimitingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RateLimitingConfig {

    @Bean
    @ConditionalOnProperty(name = "application.ratelimiting.enabled", havingValue = "true")
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RateLimitingFilter());
        registrationBean.addUrlPatterns("/api/public/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}