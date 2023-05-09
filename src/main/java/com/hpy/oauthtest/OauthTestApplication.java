package com.hpy.oauthtest;

import com.hpy.oauthtest.domain.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan("com.hpy.oauthtest.mapper")
public class OauthTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(OauthTestApplication.class, args);
    }

}
