package io.github.ponderyao.boot.model.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.github.ponderyao.boot.annotation.RegexValidate;
import io.github.ponderyao.boot.common.ValidationOrderGroup;
import io.github.ponderyao.boot.constant.RegexConstant;
import lombok.Data;

/**
 * UserRegisterDTO: 用户注册请求对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class UserRegisterDTO implements Serializable {
    
    private static final long serialVersionUID = 3762832385489627320L;
    
    @NotBlank(message = "账号不能为空", groups = {ValidationOrderGroup.First.class})
    @RegexValidate(regex = RegexConstant.ACCOUNT, message = "账号格式不符合规范", groups = {ValidationOrderGroup.Second.class})
    private String account;

    @NotBlank(message = "密码不能为空", groups = {ValidationOrderGroup.Second.class})
    @RegexValidate(regex = RegexConstant.PASSWORD, message = "密码格式不符合规范", groups = {ValidationOrderGroup.Third.class})
    private String password;

    @NotBlank(message = "确认密码不能为空", groups = {ValidationOrderGroup.Third.class})
    private String checkPassword;
    
}
