package io.github.poj.constant;

/**
 * RegexConstant: 正则表达式常量
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RegexConstant {

    /**
     * 账号：长度4~16位，只允许包含大写字母、小写字母、数字
     */
    public static final String ACCOUNT = "^[A-Za-z0-9]{4,16}$";

    /**
     * 密码：长度8~16位，必须包含大写字母、小写字母、数字
     */
    public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,16}$";

    /**
     * 用户昵称：长度4~20位，只允许包含大写字母、小写字母、中文字符（一个中文字符通常等于2个单位长度）
     */
    public static final String USERNAME = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{4,20}$";

    /**
     * 排序字段：只允许包含大写字母、小写字母、数字、下划线，且第一个字符必须是小写字母
     */
    public static final String ORDER_FIELD = "^[a-z][a-zA-Z0-9_]*$";
    
}
