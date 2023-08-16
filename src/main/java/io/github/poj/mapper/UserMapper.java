package io.github.poj.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.poj.model.entity.User;

/**
 * UserMapper
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
