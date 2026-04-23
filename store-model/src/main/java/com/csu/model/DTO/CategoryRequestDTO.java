package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 商品分类请求数据传输对象（DTO）
 * 用于后台管理系统新增或修改商品分类时接收请求参数
 */
@Data
public class CategoryRequestDTO {
    /** 分类ID（必填，唯一标识） */
    @NotBlank(message = "分类ID不能为空")
    private String categoryId;

    /** 分类名称（必填） */
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /** 分类描述（可选） */
    private String description;
}
