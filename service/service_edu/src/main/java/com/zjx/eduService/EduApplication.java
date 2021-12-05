package com.zjx.eduService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zjx"})
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();

        c.setIgnoreUnresolvablePlaceholders(true);

        return c;
    }

}
