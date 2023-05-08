package com.hpy.oauthtest.service;

import com.hpy.oauthtest.domain.AjaxResult;

public interface UserService {

    AjaxResult login(String username,String password);
}
