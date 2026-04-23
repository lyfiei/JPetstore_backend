package com.csu.model.Enum;

/**
 * 支付方式枚举类
 * 定义支持的信用卡支付类型
 */
public enum PaymentMethodEnum {
    /** Visa信用卡 */
    VISA("Visa"),
    
    /** Mastercard信用卡 */
    MASTERCARD("Mastercard"),
    
    /** American Express信用卡 */
    AMEX("American Express");

    private final String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 获取支付方式显示名称
     * @return 显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
}