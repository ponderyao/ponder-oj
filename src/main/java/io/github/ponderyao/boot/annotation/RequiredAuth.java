package io.github.ponderyao.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.ponderyao.boot.model.enums.user.SystemRole;

/**
 * RequiredAuth
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredAuth {
    
    SystemRole value() default SystemRole.USER;
    
}
