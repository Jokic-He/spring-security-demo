package com.hpy.oauthtest.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpy.oauthtest.domain.SysUser;
import com.hpy.oauthtest.domain.UserDO;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-06
 */
@RequiredArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService, UserDetailsPasswordService {


    public static Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = new UserDO().selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, username));
        if (userDO != null) {
            //从数据库中获取用户，
            Set<SimpleGrantedAuthority> permissions = Set.of("admin:read", "admin:create", "admin:update", "admin:delete").stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toSet());
            //生成用户的token
            var sysUser = new SysUser().setId(1).setPassword(userDO.getPassword()).setUsername(username).setAuthorities(permissions);
            return sysUser;
        }
        return null;

    }
}

