package io.github.ponderyao.boot.model.view.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * UserVO: 用户视图
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class UserVO implements Serializable {
    
    private static final long serialVersionUID = 1248134505493940905L;
    
    private Long userId;
    
    private String username;
    
    private String avatar;
    
    private String profile;
    
    private String systemRole;
    
    private Date createTime;
    
}
