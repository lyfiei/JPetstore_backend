 package com.csu.model.Enum;

public enum OrderStatusEnum {
    PENDING("P", "待处理"),
    APPROVED("A", "已批准"),
    REJECTED("R", "已拒绝"),
    SHIPPED("S", "已发货");

    private final String code;
    private final String description;

    OrderStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatusEnum fromCode(String code) {
        for (OrderStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown order status code: " + code);
    }
}
