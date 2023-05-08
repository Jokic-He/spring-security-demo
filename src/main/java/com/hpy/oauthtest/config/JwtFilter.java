package com.hpy.oauthtest.config;

import cn.hutool.core.util.StrUtil;
import com.hpy.oauthtest.domain.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * jwt验证token
 *
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final MyUserDetailService myUserDetailService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        //如果是登录接口就自动放行
        if (req.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(req, res);
            return;
        }
        //从header中获取token
        final String authHeader = req.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.getWriter().write("访问该资源请先授权");
            return;
        }
        //截取有效的token字符串
        jwt = authHeader.substring(7);
        //解析jwt获取用户名
        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SysUser sysUser = (SysUser) myUserDetailService.loadUserByUsername(username);
            //TODO 校验token准确性和是否过期
            boolean isTokenValid = false;
            if (jwtService.isTokenValid(jwt, sysUser) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        sysUser,
                        null,
                        sysUser.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }


        filterChain.doFilter(req, res);
    }
}

