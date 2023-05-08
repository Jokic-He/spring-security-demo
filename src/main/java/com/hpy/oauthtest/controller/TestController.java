package com.hpy.oauthtest.controller;


import com.hpy.oauthtest.config.MyUserDetailService;
import com.hpy.oauthtest.domain.AjaxResult;
import com.hpy.oauthtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final UserService userService;


    @PostMapping("/api/v1/login")
    public AjaxResult login(String username, String password) {
        return userService.login(username, password);
    }


    @GetMapping("/api/v1/test")
    @PreAuthorize("hasAnyAuthority('admin:ctreate')")
    public AjaxResult test() {
        System.out.println("当前用户为:" + MyUserDetailService.getCurrentUser().getUsername());
        return AjaxResult.success();
    }
}
