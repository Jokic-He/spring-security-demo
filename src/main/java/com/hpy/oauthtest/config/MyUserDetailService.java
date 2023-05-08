package com.hpy.oauthtest.config;

import com.hpy.oauthtest.domain.SysUser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-06
 */
@RequiredArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService, UserDetailsPasswordService {


    public SysUser getCurrentUser() {
        return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public void setCurrentUser(SysUser sysUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(sysUser, sysUser.getUsername());
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 根据username从数据库中获取用户

        return null;
    }
}

