package io.github.ponderyao.boot.utils;

import java.util.UUID;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.ponderyao.boot.constant.SymbolConstant;

/**
 * RandomUtils: 随机生成工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RandomUtils {
    
    public static final String DEFAULT_DATE_PATTERN = "yyyyMMddHHmmssSSS";
    
    public static String randomUniqueName() {
        return randomUniqueName(DEFAULT_DATE_PATTERN);
    }

    /**
     * 随机生成唯一名
     * 
     * @param datePattern 时间格式
     * @return 随机生成的唯一名
     */
    public static String randomUniqueName(String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        String datetime = DateTimeUtils.dateToString(datePattern);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return datetime + SymbolConstant.HYPHEN + uuid;
    }
    
}
