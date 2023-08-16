package io.github.poj.model.enums;

/**
 * SessionAttribute: session记录属性
 *
 * @author PonderYao
 * @since 1.0.0
 */
public enum SessionAttribute {

    USER_LOGIN_STATE("loginUser", "用户登录状态"),
    ;

    private final String code;

    private final String desc;

    SessionAttribute(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
