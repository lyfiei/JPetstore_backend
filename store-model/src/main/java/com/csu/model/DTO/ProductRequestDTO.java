package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 产品请求数据传输对象（DTO）
 * 用于后台管理系统新增或修改产品时接收请求参数
 */
@Data
public class ProductRequestDTO {
    /** 产品ID（必填，唯一标识） */
    @NotBlank(message = "产品ID不能为空")
    private String productId;

    /** 分类ID（必填，关联到category表） */
    @NotBlank(message = "分类ID不能为空")
    private String categoryId;

    /** 产品名称（必填） */
    @NotBlank(message = "产品名称不能为空")
    private String name;

    /** 产品描述（可选） */
    private String description;
}
