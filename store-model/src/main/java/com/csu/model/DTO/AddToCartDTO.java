package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class AddToCartDTO {
    @NotBlank(message = "商品ID不能为空")
    private String itemId;

    @Min(value = 1, message = "数量至少为1")
    private Integer quantity = 1;
}