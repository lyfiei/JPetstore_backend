package com.csu.model.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private String itemId;
    private String productId;
    private BigDecimal listPrice;
    private Integer quantity;
    private Boolean inStock;
}