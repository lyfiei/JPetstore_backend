package com.csu.model.Enum;

/**
 * 账户状态枚举类
 * 定义用户账户的所有可能状态
 */
public enum AccountStatusEnum {
    /** 正常状态 */
    ACTIVE("OK", "正常"),
    
    /** 禁用状态 */
    INACTIVE("OFF", "禁用");

    private final String code;
    private final String description;

    AccountStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取状态描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据状态代码获取枚举值
     * @param code 状态代码
     * @return 对应的枚举值
     * @throws IllegalArgumentException 如果代码不存在
     */
    public static AccountStatusEnum fromCode(String code) {
        for (AccountStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown account status code: " + code);
    }
}
