package io.github.poj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.poj.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: 用户实体
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class User extends BaseEntity {

    @TableField(exist = false)
    private static final long serialVersionUID = -693034245208679252L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    
    private String account;
    
    private String password;
    
    private String passwordSalt;
    
    private String username;
    
    private String avatar;
    
    private String profile;
    
    private String systemRole;
    
}
