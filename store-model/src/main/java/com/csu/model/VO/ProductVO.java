package com.csu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 产品视图对象（VO）
 * 用于展示产品信息及其关联的商品（SKU）列表
 */
@Data
public class ProductVO {
    private String productId;
    private String categoryId;
    private String categoryName;
    private String name;
    private String description;

    private List<ItemVO> itemList;
}
