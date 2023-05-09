package com.hpy.oauthtest.controller;


import com.hpy.oauthtest.config.MyUserDetailService;
import com.hpy.oauthtest.domain.AjaxResult;
import com.hpy.oauthtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/api/v1/auth/login")
    public AjaxResult login(String username, String password) {
        String encode = passwordEncoder.encode("123456");
        return userService.login(username, password);
    }


    @GetMapping("/api/v1/testAuth")
    @PreAuthorize("hasAuthority('admin:create')")
    public AjaxResult testAuth() {
        System.out.println("当前用户为:" + MyUserDetailService.getCurrentUser().getPrincipal());
        return AjaxResult.success("本接口你有权限");
    }


    @GetMapping("/api/v1/testUnAuth")
    @PreAuthorize("hasAuthority('admin:noauth')")
    public AjaxResult testUnAuth() {
        System.out.println("当前用户为:" + MyUserDetailService.getCurrentUser().getPrincipal());
        return AjaxResult.success("本接口你没有权限");
    }

    @GetMapping("/api/v1/testNoAuth")
    public AjaxResult testNoAuth() {
        System.out.println("当前用户为:" + MyUserDetailService.getCurrentUser().getPrincipal());
        return AjaxResult.success("本接口不需要权限");
    }


    @RequestMapping("/pass")
    public void pass(String password) {
        System.out.println("生成密码:"+passwordEncoder.encode(password));
    }
}
