package com.csu.model.VO;

import lombok.Data;
import java.math.BigDecimal;

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
