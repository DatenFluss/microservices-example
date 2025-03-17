package com.example.client.config;

import com.example.client.security.SidValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    
    @Bean
    public FilterRegistrationBean<SidValidationFilter> sidValidationFilter() {
        FilterRegistrationBean<SidValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SidValidationFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
} 