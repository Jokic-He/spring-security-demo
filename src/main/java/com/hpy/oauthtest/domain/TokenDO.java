package com.hpy.oauthtest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * token实体类
 */
@Data
@TableName("sys_token")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TokenDO extends Model<TokenDO> {


    @TableId(type = IdType.AUTO)
    private Integer id;

    private String token;

    //是否失效(这里指手动失效)
    private boolean revoked;

    //是否过期
    private boolean expired;

    private Integer userId;
}
