package io.github.ponderyao.boot.model.dto.user;

import java.util.Date;

import io.github.ponderyao.boot.annotation.EnumValidate;
import io.github.ponderyao.boot.common.PageQuery;
import io.github.ponderyao.boot.model.enums.user.SystemRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserPageQryDTO: 用户信息分页查询请求对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageQryDTO extends PageQuery {
    
    private static final long serialVersionUID = 2041909164304712268L;
    
    private String username;
    
    @EnumValidate(value = SystemRole.class, nullable = true, message = "系统角色不存在")
    private String systemRole;
    
    private Date startTime;
    
    private Date endTime;
    
}
