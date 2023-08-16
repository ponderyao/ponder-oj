package io.github.poj.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.poj.model.dto.user.UserPageQryDTO;
import io.github.poj.model.entity.User;

/**
 * UserService: 用户服务
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 注册用户
     * 
     * @param account 账号
     * @param password 密码
     * @return 用户标识
     */
    Long registerUser(String account, String password);

    /**
     * 用户登录
     * 
     * @param account 账号
     * @param password 密码
     * @return 登录用户
     */
    User userLogin(String account, String password);

    /**
     * 修改用户信息
     * 
     * @param user 用户
     * @return 是否成功
     */
    boolean modifyUser(User user);

    /**
     * 查询用户
     * 
     * @param userId 用户标识
     * @return 用户视图
     */
    User queryUserById(Long userId);

    /**
     * 分页查询用户
     * 
     * @param dto 用户信息分页查询对象
     * @return 用户分页信息
     */
    Page<User> queryUserPage(UserPageQryDTO dto);

    /**
     * 获取当前登录用户信息
     * 
     * @param request 请求
     * @return 当前登录用户
     */
    User getLoginUser(HttpServletRequest request);
    
}
