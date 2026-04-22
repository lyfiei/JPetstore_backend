package com.csu.model.Enum;

public enum PaymentMethodEnum {
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMEX("American Express");

    private final String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}