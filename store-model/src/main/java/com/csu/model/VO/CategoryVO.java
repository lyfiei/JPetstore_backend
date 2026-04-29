package com.csu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品分类视图对象（VO）
 * 用于展示分类信息及其关联的产品列表
 */
@Data
public class CategoryVO {
    private String categoryId;
    private String categoryName;
    private List<ProductVO> productList;
    private String description;
}

