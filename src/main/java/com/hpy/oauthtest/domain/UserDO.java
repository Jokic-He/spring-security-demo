package com.hpy.oauthtest.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Data
public class UserDO extends Model<UserDO> {


    private Integer id;

    private String username;

    private String password;

    private String name;
}

