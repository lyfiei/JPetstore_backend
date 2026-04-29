package com.csu.model.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 购物车项视图对象（VO）
 * 用于向前端展示购物车中单个商品的详细信息
 */
@Data
public class CartItemVO {
    private ItemVO item;
    private Integer quantity;
    private Boolean inStock;
    private BigDecimal total;

    public BigDecimal getTotal() {
        if (item != null && item.getListPrice() != null && quantity != null) {
            return item.getListPrice().multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
}
