package com.app.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AuthApplication  { // extends SpringBootServletInitializer

    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    //     return application.sources(AuthApplication.class);
    // }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
	
