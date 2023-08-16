package io.github.ponderyao.boot.model.enums.user;

/**
 * SystemRole: 系统角色
 *
 * @author PonderYao
 * @since 1.0.0
 */
public enum SystemRole {
    
    USER("user", "普通用户"),
    ADMIN("admin", "系统管理员"),
    BAN("ban", "封号用户"),
    ;

    private final String code;
    
    private final String desc;
    
    SystemRole(String code, String desc) {
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
