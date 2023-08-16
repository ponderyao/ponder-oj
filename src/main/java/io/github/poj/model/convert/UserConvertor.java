package io.github.poj.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.poj.model.dto.user.UserEditDTO;
import io.github.poj.model.entity.User;
import io.github.poj.model.view.user.LoginUserVO;
import io.github.poj.model.view.user.UserVO;

/**
 * UserConvertor: 用户数据转换器
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    UserVO entityToView(User user);
    
    LoginUserVO entityToLoginView(User user);
    
    @Mapping(target = "optimizeJoinOfCountSql", ignore = true)
    Page<UserVO> entityToView(Page<User> userPage);
    
    @Mappings({
        @Mapping(target = "userId", ignore = true),
        @Mapping(target = "account", ignore = true),
        @Mapping(target = "password", ignore = true),
        @Mapping(target = "passwordSalt", ignore = true),
        @Mapping(target = "systemRole", ignore = true),
        @Mapping(target = "createTime", ignore = true),
        @Mapping(target = "updateTime", ignore = true),
        @Mapping(target = "valid", ignore = true),
    })
    User dtoToEntity(UserEditDTO dto, @MappingTarget User loginUser);
    
}
