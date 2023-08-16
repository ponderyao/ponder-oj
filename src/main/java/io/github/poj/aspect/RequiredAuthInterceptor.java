package io.github.poj.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.poj.annotation.RequiredAuth;
import io.github.poj.common.ErrorCode;
import io.github.poj.exception.ThrowUtils;
import io.github.poj.model.entity.User;
import io.github.poj.model.enums.user.SystemRole;
import io.github.poj.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * RequiredAuthInterceptor: 所需权限校验切面
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RequiredAuthInterceptor {
    
    private final UserService userService;
    
    @Before("@annotation(requiredAuth)")
    public void doInterceptor(JoinPoint joinPoint, RequiredAuth requiredAuth) {
        // 获取当前登录用户信息
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        // 校验系统角色与所需权限角色是否一致
        SystemRole requiredRole = requiredAuth.value();
        boolean roleMatched = StringUtils.equals(requiredRole.getCode(), loginUser.getSystemRole());
        ThrowUtils.throwIf(!roleMatched, ErrorCode.NO_AUTH_ERROR, "当前用户无访问权限");
    }
    
}
