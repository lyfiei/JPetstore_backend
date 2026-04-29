package com.csu.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车视图对象（VO）
 * 用于向前端展示完整的购物车信息，包含所有购物车项和总计
 */
@Data
public class CartVO {
    /** 购物车中商品种类数量 */
    private Integer numberOfItems;
    
    /** 购物车项列表 */
    private List<CartItemVO> cartItems;
    
    /** 购物车总金额（所有商品小计之和） */
    private BigDecimal subTotal;
}
