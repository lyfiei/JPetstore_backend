package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表视图对象（VO）
 * 用于后台管理系统展示订单列表摘要信息
 */
@Data
public class OrderListVO {
    /** 订单ID */
    private int orderId;
    private String userId;
    
    /** 客户姓名（firstName + lastName） */
    private String customerName;
    
    /** 订单创建时间 */
    private Date orderDate;
    
    /** 订单状态码（P/A/R/S） */
    private String status;
    
    /** 订单状态描述（待处理/已批准/已拒绝/已发货） */
    private String statusDescription;
    
    /** 订单总金额 */
    private BigDecimal totalPrice;
    
    /** 订单商品项数量 */
    private Integer itemCount;
}
