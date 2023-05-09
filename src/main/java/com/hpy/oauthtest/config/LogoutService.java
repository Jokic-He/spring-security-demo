package com.hpy.oauthtest.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpy.oauthtest.domain.TokenDO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * 登出具体实现类
 */
@Component
public class LogoutService implements LogoutHandler {


    /**
     * 登出实现
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //先获取token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        //从db或者redis中获取token并且对比
        TokenDO tokenDO = new TokenDO().selectOne(new LambdaQueryWrapper<TokenDO>().eq(TokenDO::getToken, jwt));
        if (tokenDO != null) {
            tokenDO.setExpired(true);
            tokenDO.setRevoked(true);
            tokenDO.insertOrUpdate();
            SecurityContextHolder.clearContext();
        }

    }
}
