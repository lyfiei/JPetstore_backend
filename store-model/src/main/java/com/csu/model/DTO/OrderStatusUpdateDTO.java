package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 订单状态更新请求数据传输对象（DTO）
 * 用于后台管理系统修改订单状态时接收请求参数
 */
@Data
public class OrderStatusUpdateDTO {
    /** 订单ID（必填） */
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;

    /** 订单状态（必填，P=待处理/A=已批准/R=已拒绝/S=已发货） */
    @NotBlank(message = "订单状态不能为空")
    private String status;
}
