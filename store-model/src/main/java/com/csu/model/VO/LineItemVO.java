package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineItemVO {
    private int orderId;
    private int lineNumber;
    private int quantity;
    private String itemId;
    private BigDecimal unitPrice;
    private ItemVO item;
    private BigDecimal total;

    public BigDecimal getTotal() {
        if (unitPrice != null) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
}
