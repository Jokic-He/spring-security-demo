package com.hpy.oauthtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hpy.oauthtest.mapper")
public class OauthTestApplication {

    public static final String REDIS_TOKEN_KEY="login:token:{}";


    public static void main(String[] args) {
        SpringApplication.run(OauthTestApplication.class, args);
    }

}
