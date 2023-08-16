package io.github.ponderyao.boot.repository.impl;

import java.util.Objects;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.ponderyao.boot.constant.CacheConstant;
import io.github.ponderyao.boot.constant.SqlConstant;
import io.github.ponderyao.boot.mapper.UserMapper;
import io.github.ponderyao.boot.model.dto.user.UserPageQryDTO;
import io.github.ponderyao.boot.model.entity.User;
import io.github.ponderyao.boot.repository.UserRepository;

/**
 * UserRepositoryImpl: 用户数据仓储实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements UserRepository {

    @Override
    @Cacheable(value = CacheConstant.USER_CACHE, key = "#userId")
    public User selectUserById(Long userId) {
        return getById(userId);
    }

    @Override
    public User selectUserByAccount(String account) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getAccount, account);
        return getOne(wrapper);
    }

    @Override
    public Page<User> selectUserPage(UserPageQryDTO dto) {
        Page<User> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dto.getUsername()), "username", dto.getUsername());
        wrapper.eq(StringUtils.isNotBlank(dto.getSystemRole()), "system_role", dto.getSystemRole());
        wrapper.ge(Objects.nonNull(dto.getStartTime()), "create_time", dto.getStartTime());
        wrapper.lt(Objects.nonNull(dto.getEndTime()), "create_time", dto.getEndTime());
        wrapper.orderBy(StringUtils.isNotBlank(dto.getOrderField()), StringUtils.equals(dto.getOrderByWay(), SqlConstant.ASC),
            dto.getOrderField());
        return page(page, wrapper);
    }

    @Override
    @CachePut(value = CacheConstant.USER_CACHE, key = "#userId")
    public User updateUserById(User user, Long userId) {
        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getUserId, userId);
        wrapper.set(User::getUsername, user.getUsername())
            .set(User::getAvatar, user.getAvatar())
            .set(User::getProfile, user.getProfile())
            .set(User::getSystemRole, user.getSystemRole());
        boolean updateResult = update(wrapper);
        return updateResult ? user : null;
    }

}
