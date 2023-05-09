package com.hpy.oauthtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hpy.oauthtest.domain.TokenDO;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenMapper extends BaseMapper<TokenDO> {
}
