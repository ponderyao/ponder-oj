package io.github.ponderyao.boot.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.ponderyao.boot.model.dto.user.UserPageQryDTO;
import io.github.ponderyao.boot.model.entity.User;

/**
 * UserRepository: 用户数据仓储
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface UserRepository extends IService<User> {
    
    User selectUserById(Long userId);
    
    User selectUserByAccount(String account);
    
    Page<User> selectUserPage(UserPageQryDTO dto);
    
    User updateUserById(User user, Long userId);
    
}
