package com.hpy.oauthtest.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpy.oauthtest.OauthTestApplication;
import com.hpy.oauthtest.config.JwtService;
import com.hpy.oauthtest.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final StringRedisTemplate stringRedisTemplate;

    private final int tokenTimeOut=60*1000;


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public AjaxResult login(String username, String password) {
        //简单测试security内容暂时不考虑校验用户名密码复杂度等
        UserDO userDO = new UserDO().selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, username));
        //不校验用户是否被删除或者禁用
        //校验密码是否正确
        if (passwordEncoder.matches(password, userDO.getPassword())) {
            //用户校验成功，从数据库中查询出用户的权限，现在直接写死
            Set<SimpleGrantedAuthority> permissions = Set.of("admin:read", "admin:create", "admin:update", "admin:delete").stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toSet());
            //生成用户的token
            var sysUser = new SysUser().setId(1).setPassword(userDO.getPassword()).setUsername(username).setAuthorities(permissions);
            var accssToken = jwtService.generateToken(sysUser);
            var refreshToken = jwtService.generateRefreshToken(sysUser);
            //此处有两个选择，可以把token存到db或者redis中，这里方便演示两个都保存
            new TokenDO().setToken(accssToken).setUserId(userDO.getId()).setRevoked(false).setExpired(false).insert();
            //可以配合jwt设置一个超时时间双重判断是否过期，也可以作为手动使token失效的功能
            stringRedisTemplate.opsForValue().set(StrUtil.format(OauthTestApplication.REDIS_TOKEN_KEY,accssToken), JSONUtil.toJsonStr(sysUser));

            //返回生成的两个token
            return AjaxResult.success(TokenResponse.builder().accessToken(accssToken).refreshToken(refreshToken).build());
        }
        return AjaxResult.error();
    }


}
