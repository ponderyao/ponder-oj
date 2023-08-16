package io.github.ponderyao.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.ponderyao.boot.model.entity.User;

/**
 * UserMapper
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
