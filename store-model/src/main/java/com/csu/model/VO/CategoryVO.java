package com.csu.model.VO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    private String categoryId;
    private String categoryName;
    private List<ProductVO> productList;
}

