package io.github.ponderyao.boot.service.impl;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ponderyao.boot.common.ErrorCode;
import io.github.ponderyao.boot.exception.ThrowUtils;
import io.github.ponderyao.boot.model.dto.user.UserPageQryDTO;
import io.github.ponderyao.boot.model.entity.User;
import io.github.ponderyao.boot.model.enums.SessionAttribute;
import io.github.ponderyao.boot.model.view.user.LoginUserVO;
import io.github.ponderyao.boot.repository.UserRepository;
import io.github.ponderyao.boot.service.UserService;
import io.github.ponderyao.boot.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;

/**
 * UserServiceImpl: 用户服务实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public Long registerUser(String account, String password) {
        synchronized (account.intern()) {
            // 账号存在性校验
            User user = userRepository.selectUserByAccount(account);
            ThrowUtils.throwIf(Objects.nonNull(user), ErrorCode.OPERATION_ERROR, String.format("账号%s已存在", account));
            // 生成密码盐后密码加密
            String passwordSalt = PasswordUtils.generatePasswordSalt();
            String encryptedPassword = PasswordUtils.encryptPassword(password, passwordSalt);
            // 保存用户
            user = new User();
            user.setAccount(account);
            user.setPassword(encryptedPassword);
            user.setPasswordSalt(passwordSalt);
            user.setValid(true);
            boolean saveResult = userRepository.save(user);
            ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "注册失败！系统异常，请稍后再试");
            return user.getUserId();
        }
    }

    @Override
    public User userLogin(String account, String password) {
        // 根据账号查询用户
        User user = userRepository.selectUserByAccount(account);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.PARAMS_ERROR, "账号不存在或密码错误");
        // 密码加密后对比是否一致
        String encryptedPassword = PasswordUtils.encryptPassword(password, user.getPasswordSalt());
        boolean passwordEqual = StringUtils.equals(encryptedPassword, user.getPassword());
        ThrowUtils.throwIf(!passwordEqual, ErrorCode.PARAMS_ERROR, "账号不存在或密码错误");
        // 重新查询用户，将信息加载到缓存
        return userRepository.selectUserById(user.getUserId());
    }

    @Override
    public boolean modifyUser(User user) {
        return Objects.nonNull(userRepository.updateUserById(user, user.getUserId()));
    }

    @Override
    public User queryUserById(Long userId) {
        User user = userRepository.selectUserById(userId);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        return user;
    }

    @Override
    public Page<User> queryUserPage(UserPageQryDTO dto) {
        return userRepository.selectUserPage(dto);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object userLoginAttribute = request.getSession().getAttribute(SessionAttribute.USER_LOGIN_STATE.getCode());
        boolean isLogin = Objects.nonNull(userLoginAttribute) && (userLoginAttribute instanceof LoginUserVO);
        ThrowUtils.throwIf(!isLogin, ErrorCode.NOT_LOGIN_ERROR, "未登录");
        return userRepository.selectUserById(((LoginUserVO) userLoginAttribute).getUserId());
    }

}
