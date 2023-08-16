package io.github.ponderyao.boot.model.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.github.ponderyao.boot.annotation.RegexValidate;
import io.github.ponderyao.boot.common.ValidationOrderGroup;
import io.github.ponderyao.boot.constant.RegexConstant;
import lombok.Data;

/**
 * UserLoginDTO: 用户登录请求对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class UserLoginDTO implements Serializable {
    
    private static final long serialVersionUID = -2130227031457959861L;

    @NotBlank(message = "账号不能为空", groups = {ValidationOrderGroup.First.class})
    @RegexValidate(regex = RegexConstant.ACCOUNT, message = "账号不存在或密码错误", groups = {ValidationOrderGroup.Second.class})
    private String account;

    @NotBlank(message = "密码不能为空", groups = {ValidationOrderGroup.Second.class})
    @RegexValidate(regex = RegexConstant.PASSWORD, message = "账号不存在或密码错误", groups = {ValidationOrderGroup.Third.class})
    private String password;
    
}
