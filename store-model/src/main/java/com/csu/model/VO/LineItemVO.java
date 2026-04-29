package com.csu.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单项视图对象（VO）
 * 用于展示订单中的单个商品项信息
 */
@Data
public class LineItemVO {
    private int orderId;
    private int lineNumber; //行号（订单内序号）
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
