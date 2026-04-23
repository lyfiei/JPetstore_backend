package com.csu.model.DTO;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车数据传输对象（DTO）
 * 用于在业务层传输购物车数据，以Map结构存储购物车项
 */
@Data
public class CartDTO {
    /** 购物车项Map，key为商品ID，value为购物车项详情 */
    private Map<String, CartItemDTO> itemMap = new HashMap<>();
}
