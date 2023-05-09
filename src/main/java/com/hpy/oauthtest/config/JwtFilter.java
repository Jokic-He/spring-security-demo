package com.hpy.oauthtest.config;

import cn.hutool.core.util.StrUtil;
import com.hpy.oauthtest.OauthTestApplication;
import com.hpy.oauthtest.domain.SysUser;
import com.hpy.oauthtest.exception.NoTokenException;
import com.hpy.oauthtest.exception.TokenVerifyErrorException;
import com.hpy.oauthtest.exception.UnAuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * token校验过滤器，校验通过后将用户保存在上下文中
 *
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final MyUserDetailService myUserDetailService;

    private final JwtService jwtService;

    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException, TokenVerifyErrorException, NoTokenException {

        //如果是登录接口就自动放行
        if (req.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(req, res);
            return;
        }
        //从header中获取token
        final String authHeader = req.getHeader("Authorization");
        final String jwt;
        String username = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NoTokenException();
        }
        //截取有效的token字符串
        jwt = authHeader.substring(7);
        //解析jwt获取用户名
        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            log.error("token格式异常:{}", e);
            throw new TokenVerifyErrorException();
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SysUser sysUser = (SysUser) myUserDetailService.loadUserByUsername(username);
            boolean isTokenValid = true;
            //TODO 校验token准确性和是否过期，此处可以自定义校验，可以使用jwt自带的校验也可以通过数据库和redis等共同判断
            String userInfo= (String) redisTemplate.opsForValue().get(StrUtil.format(OauthTestApplication.REDIS_TOKEN_KEY,jwt));
            if(StrUtil.isNotBlank(userInfo))isTokenValid=false;
            if (sysUser != null && jwtService.isTokenValid(jwt, sysUser) && isTokenValid) {
                /**
                 * 封装成用户的凭证，传入用户实体类和用户的权限集合，spring security会自动去判断是否拥有权限
                 */
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        sysUser,
                        null,
                        sysUser.getAuthorities()
                );
                /**
                 * 在 Spring Security 中，当用户进行认证时，会先通过 AuthenticationFilter 进行请求过滤，
                 * 然后调用 AuthenticationManager 进行认证。在认证过程中，Spring Security 会自动创建一个
                 * Authentication 对象，其中包含了认证请求的相关信息。如果你需要自定义 Authentication
                 * 对象的详细信息，就可以使用 WebAuthenticationDetailsSource 来创建 WebAuthenticationDetails 对象。
                 */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }


        filterChain.doFilter(req, res);
    }


}

