package com.csu.model.DTO;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 购物车项数据传输对象（DTO）
 * 用于在业务层传输单个购物车项的信息
 */
@Data
public class CartItemDTO {
    private String itemId;
    private String productId;
    
    /** 商品单价 */
    private BigDecimal listPrice;
    
    /** 购买数量 */
    private Integer quantity;
    
    /** 是否有库存 */
    private Boolean inStock;
    
    /**
     * 计算小计金额（单价 × 数量）
     */
    public BigDecimal getSubtotal() {
        if (listPrice != null && quantity != null) {
            return listPrice.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
}