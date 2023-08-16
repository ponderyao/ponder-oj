package io.github.ponderyao.boot.model.view.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * LoginUserVO: 登录返回的用户视图
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class LoginUserVO implements Serializable {
    
    private static final long serialVersionUID = -9152470189007034202L;
    
    private Long userId;
    
    private String username;
    
    private String avatar;
    
    private String profile;
    
    private String systemRole;
    
    private Date createTime;
    
    private Date updateTime;
    
}
