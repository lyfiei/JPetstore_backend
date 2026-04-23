package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 订单创建请求数据传输对象（DTO）
 * 用于接收用户下单时提交的收货地址、账单地址和支付信息
 */
@Data
public class OrderRequestDTO {
    /** 收货地址行1（必填） */
    @NotBlank(message = "收货地址不能为空")
    private String shipAddress1;

    private String shipAddress2;

    /** 收货城市（必填） */
    @NotBlank(message = "收货城市不能为空")
    private String shipCity;

    /** 收货州/省（必填） */
    @NotBlank(message = "收货州/省不能为空")
    private String shipState;

    /** 收货邮编（必填） */
    @NotBlank(message = "收货邮编不能为空")
    private String shipZip;

    /** 收货国家（必填） */
    @NotBlank(message = "收货国家不能为空")
    private String shipCountry;

    /** 账单地址行1（必填） */
    @NotBlank(message = "账单地址不能为空")
    private String billAddress1;

    private String billAddress2;

    /** 账单城市（必填） */
    @NotBlank(message = "账单城市不能为空")
    private String billCity;

    /** 账单州/省（必填） */
    @NotBlank(message = "账单州/省不能为空")
    private String billState;

    /** 账单邮编（必填） */
    @NotBlank(message = "账单邮编不能为空")
    private String billZip;

    /** 账单国家（必填） */
    @NotBlank(message = "账单国家不能为空")
    private String billCountry;

    /** 信用卡号（必填） */
    @NotBlank(message = "信用卡号不能为空")
    private String creditCard;

    /** 信用卡过期日期（必填，格式：MM/YY） */
    @NotBlank(message = "过期日期不能为空")
    private String expiryDate;

    /** 信用卡类型（必填，如Visa/Mastercard） */
    @NotBlank(message = "卡类型不能为空")
    private String cardType;
}