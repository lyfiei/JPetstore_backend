package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVO {
    private String itemId;

    private String productId;
    private String productName;
    //private String description;
    private String descriptionImage; //由于Thymeleaf无法像jsp一样自动识别description中的<img src="">，所以这里把<img src="">和后面的text分开来写
    private String descriptionText;

    private BigDecimal listPrice;
    private String attributes;

    private Integer quantity;
}
