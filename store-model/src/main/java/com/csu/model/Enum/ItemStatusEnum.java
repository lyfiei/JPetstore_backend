package com.csu.model.Enum;

/**
 * 商品状态枚举类
 * 定义商品（SKU）的所有可能状态
 */
public enum ItemStatusEnum {
    AVAILABLE("P", "可售"),
    OUT_OF_STOCK("O", "缺货"),
    DISCONTINUED("D", "已下架");

    private final String code;
    private final String description;

    ItemStatusEnum(String code, String description) {
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
    public static ItemStatusEnum fromCode(String code) {
        for (ItemStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown item status code: " + code);
    }
}
