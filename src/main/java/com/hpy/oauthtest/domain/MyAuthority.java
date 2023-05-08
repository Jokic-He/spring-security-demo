package com.hpy.oauthtest.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Data
@Builder
public class MyAuthority implements GrantedAuthority {


    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}

