package io.github.ponderyao.boot.utils;

import java.util.UUID;

import org.springframework.util.DigestUtils;

import io.github.ponderyao.boot.constant.SymbolConstant;

/**
 * PasswordUtils: 密码工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class PasswordUtils {

    /**
     * 基于UUID生成密码盐
     * 
     * @return 密码盐
     */
    public static String generatePasswordSalt() {
        return UUID.randomUUID().toString().replaceAll(SymbolConstant.HYPHEN, SymbolConstant.EMPTY);
    }

    /**
     * 根据密码盐使用MD5摘要算法加密密码
     * 
     * @param password 密码
     * @param salt 密码盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        int half = salt.length() >> 2;
        String text = salt.substring(0, half) + password + salt.substring(half);
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }
    
}
