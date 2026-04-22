package com.csu.model.VO;

import lombok.Data;

import java.util.List;

@Data
public class ProductVO {
    private String productId;
    private String categoryId;
    private String productName;

    private List<ItemVO> itemList;
}
