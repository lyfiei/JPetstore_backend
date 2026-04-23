package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单详情视图对象（VO）
 * 用于后台管理系统展示订单的完整详细信息，包含收货信息、支付信息和订单状态历史
 */
@Data
public class OrderVO {
    private int orderId;
    private String userId;
    
    /** 客户姓名 */
    private String customerName;
    
    /** 订单创建时间 */
    private Date orderDate;

    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;

    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;

    /** 快递公司名称 */
    private String courier;
    private BigDecimal totalPrice;

    private String billToFirstName;
    private String billToLastName;
    private String shipToFirstName;
    private String shipToLastName;

    private String creditCard;
    
    /** 信用卡过期日期 */
    private String expiryDate;
    
    /** 信用卡类型 */
    private String cardType;

    /** 地区设置 */
    private String locale;
    
    /** 订单状态码 */
    private String status;
    
    /** 订单状态描述 */
    private String statusDescription;
    
    /** 订单项列表 */
    private List<LineItemVO> lineItems = new ArrayList<>();
    
    /** 订单状态历史记录 */
    private List<OrderStatusHistoryVO> statusHistory = new ArrayList<>();
}