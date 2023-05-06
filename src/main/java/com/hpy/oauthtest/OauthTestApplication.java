package com.hpy.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class OauthTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthTestApplication.class, args);
    }

}
