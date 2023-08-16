package io.github.ponderyao.boot.model.dto.user;

import java.io.Serializable;

import io.github.ponderyao.boot.annotation.RegexValidate;
import io.github.ponderyao.boot.constant.RegexConstant;
import lombok.Data;

/**
 * UserEditDTO: 编辑用户请求对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class UserEditDTO implements Serializable {
    
    private static final long serialVersionUID = -8223207013199672066L;
    
    @RegexValidate(regex = RegexConstant.USERNAME, message = "用户昵称格式不符合规范")
    private String username;
    
    private String avatar;
    
    private String profile;
    
}
