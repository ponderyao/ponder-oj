package io.github.poj.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.poj.annotation.RequiredAuth;
import io.github.poj.common.ErrorCode;
import io.github.poj.common.Response;
import io.github.poj.common.ValidationOrderGroup;
import io.github.poj.exception.ThrowUtils;
import io.github.poj.model.convert.UserConvertor;
import io.github.poj.model.dto.user.UserEditDTO;
import io.github.poj.model.dto.user.UserLoginDTO;
import io.github.poj.model.dto.user.UserPageQryDTO;
import io.github.poj.model.dto.user.UserRegisterDTO;
import io.github.poj.model.dto.user.UserRoleSetDTO;
import io.github.poj.model.entity.User;
import io.github.poj.model.enums.SessionAttribute;
import io.github.poj.model.enums.user.SystemRole;
import io.github.poj.model.view.user.LoginUserVO;
import io.github.poj.model.view.user.UserVO;
import io.github.poj.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * UserController: 用户接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Response<Long> userRegister(@Validated(ValidationOrderGroup.class) @RequestBody UserRegisterDTO dto) {
        boolean checkPwd = StringUtils.equals(dto.getPassword(), dto.getCheckPassword());
        ThrowUtils.throwIf(!checkPwd, ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        Long userId = userService.registerUser(dto.getAccount(), dto.getPassword());
        return Response.success(userId);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Response<LoginUserVO> userLogin(@Validated(ValidationOrderGroup.class) @RequestBody UserLoginDTO dto, HttpServletRequest request) {
        User user = userService.userLogin(dto.getAccount(), dto.getPassword());
        LoginUserVO loginUserVO = UserConvertor.INSTANCE.entityToLoginView(user);
        request.getSession().setAttribute(SessionAttribute.USER_LOGIN_STATE.getCode(), loginUserVO);
        return Response.success(loginUserVO);
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    public Response<Boolean> userLogout(HttpServletRequest request) {
        userService.getLoginUser(request);
        request.getSession().removeAttribute(SessionAttribute.USER_LOGIN_STATE.getCode());
        return Response.success(true);
    }

    /**
     * 编辑用户
     */
    @PutMapping("/edit")
    public Response<Boolean> editUser(@RequestBody UserEditDTO dto, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        loginUser = UserConvertor.INSTANCE.dtoToEntity(dto, loginUser);
        boolean operationResult = userService.modifyUser(loginUser);
        ThrowUtils.throwIf(!operationResult, ErrorCode.OPERATION_ERROR);
        // 更新用户登录信息
        LoginUserVO loginUserVO = UserConvertor.INSTANCE.entityToLoginView(loginUser);
        request.getSession().setAttribute(SessionAttribute.USER_LOGIN_STATE.getCode(), loginUserVO);
        return Response.success(true);
    }

    /**
     * 查询用户
     */
    @GetMapping("/{userId}")
    public Response<UserVO> queryUser(@PathVariable @NotNull(message = "用户标识不能为空") Long userId) {
        User user = userService.queryUserById(userId);
        UserVO userVO = UserConvertor.INSTANCE.entityToView(user);
        return Response.success(userVO);
    }

    /**
     * 分页查询用户（管理员）
     */
    @PostMapping("/page")
    @RequiredAuth(SystemRole.ADMIN)
    public Response<Page<UserVO>> queryUserPage(@Validated @RequestBody UserPageQryDTO dto) {
        boolean isTimeILLegal = Objects.nonNull(dto.getStartTime()) && Objects.nonNull(dto.getEndTime()) 
            && dto.getStartTime().compareTo(dto.getEndTime()) >= 0;
        ThrowUtils.throwIf(isTimeILLegal, ErrorCode.PARAMS_ERROR, "起始时间不能晚于或等于截止时间");
        Page<User> userPage = userService.queryUserPage(dto);
        Page<UserVO> userVOPage = UserConvertor.INSTANCE.entityToView(userPage);
        return Response.success(userVOPage);
    }

    /**
     * 设置用户角色（管理员）
     */
    @PutMapping("/role")
    @RequiredAuth(SystemRole.ADMIN)
    public Response<Boolean> setUserRole(@Validated @RequestBody UserRoleSetDTO dto) {
        User user = userService.queryUserById(dto.getUserId());
        user.setSystemRole(dto.getSystemRole());
        boolean operationResult = userService.modifyUser(user);
        ThrowUtils.throwIf(operationResult, ErrorCode.OPERATION_ERROR);
        return Response.success(true);
    }

}
