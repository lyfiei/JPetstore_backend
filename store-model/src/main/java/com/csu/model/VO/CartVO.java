package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {
    private Integer numberOfItems;
    private List<CartItemVO> cartItems;
    private BigDecimal subTotal;
}
