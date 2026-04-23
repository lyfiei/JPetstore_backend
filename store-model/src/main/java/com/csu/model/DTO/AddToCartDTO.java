package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 添加到购物车请求数据传输对象（DTO）
 * 用于接收用户添加商品到购物车时的请求参数
 */
@Data
public class AddToCartDTO {
    /** 商品ID（SKU，必填） */
    @NotBlank(message = "商品ID不能为空")
    private String itemId;

    /** 购买数量（默认为1，至少为1） */
    @Min(value = 1, message = "数量至少为1")
    private Integer quantity = 1;
}