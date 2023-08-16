package io.github.ponderyao.boot.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * DateTimeUtils
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DateTimeUtils {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static String dateToString(String pattern) {
        return dateToString(new Date(), pattern);
    }

    /**
     * 线程安全的Date转字符串方法
     *
     * @param date Date格式时间
     * @return 时间字符串
     */
    public static String dateToString(Date date) {
        return dateToString(date, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * 线程安全的字符串转Date方法
     *
     * @param dateStr 时间字符串
     * @return Date格式时间
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * 线程安全的Date转自定义格式字符串方法
     *
     * @param date Date格式时间
     * @param pattern 字符串格式
     * @return 时间字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_DATE_TIME_PATTERN;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, DEFAULT_ZONE_ID);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 线程安全的字符串转Date方法
     *
     * @param dateStr 时间字符串
     * @param pattern 字符串格式
     * @return Date格式时间
     */
    public static Date stringToDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_DATE_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    /**
     * 获取前N个时间单位的日期时间
     * 
     * @param pastNum 前N
     * @param unit 时间单位
     * @return Date格式时间
     */
    public static Date getPastUnitAgo(int pastNum, ChronoUnit unit) {
        LocalDateTime targetDateTime = LocalDateTime.now().minus(pastNum, unit);
        return Date.from(targetDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }
    
}
