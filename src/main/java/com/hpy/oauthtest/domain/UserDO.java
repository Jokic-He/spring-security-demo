package com.hpy.oauthtest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class UserDO extends Model<UserDO> {


    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String name;
}

