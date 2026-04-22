package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "收货地址不能为空")
    private String shipAddress1;

    private String shipAddress2;

    @NotBlank(message = "收货城市不能为空")
    private String shipCity;

    @NotBlank(message = "收货州/省不能为空")
    private String shipState;

    @NotBlank(message = "收货邮编不能为空")
    private String shipZip;

    @NotBlank(message = "收货国家不能为空")
    private String shipCountry;

    @NotBlank(message = "账单地址不能为空")
    private String billAddress1;

    private String billAddress2;

    @NotBlank(message = "账单城市不能为空")
    private String billCity;

    @NotBlank(message = "账单州/省不能为空")
    private String billState;

    @NotBlank(message = "账单邮编不能为空")
    private String billZip;

    @NotBlank(message = "账单国家不能为空")
    private String billCountry;

    @NotBlank(message = "信用卡号不能为空")
    private String creditCard;

    @NotBlank(message = "过期日期不能为空")
    private String expiryDate;

    @NotBlank(message = "卡类型不能为空")
    private String cardType;
}