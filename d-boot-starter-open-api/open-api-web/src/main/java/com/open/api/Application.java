package com.open.api;

import com.open.api.filter.HttpServletRequestReplacedFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(HttpServletRequestReplacedFilter());
        registration.addUrlPatterns("/api");
        registration.setName("HttpServletRequestReplacedFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    @Bean
    public HttpServletRequestReplacedFilter HttpServletRequestReplacedFilter() {
        return new HttpServletRequestReplacedFilter();
    }

}
