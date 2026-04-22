package com.csu.model.DTO;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CartDTO {
    private Map<String, CartItemDTO> itemMap = new HashMap<>();
}
