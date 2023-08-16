package io.github.ponderyao.boot.model.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.github.ponderyao.boot.annotation.EnumValidate;
import io.github.ponderyao.boot.model.enums.user.SystemRole;
import lombok.Data;

/**
 * UserRoleSetDTO
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class UserRoleSetDTO implements Serializable {
    
    private static final long serialVersionUID = 3905091164498332780L;
    
    @NotNull
    private Long userId;
    
    @EnumValidate(value = SystemRole.class, message = "系统角色不存在")
    private String systemRole;
    
}
