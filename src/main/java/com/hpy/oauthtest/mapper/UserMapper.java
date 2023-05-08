package com.hpy.oauthtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hpy.oauthtest.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: hepy
 * @CreateTime: 2023-05-08
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
