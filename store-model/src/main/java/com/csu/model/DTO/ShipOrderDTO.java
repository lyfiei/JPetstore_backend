package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 订单发货请求数据传输对象（DTO）
 * 用于后台管理系统执行订单发货操作时接收请求参数
 */
@Data
public class ShipOrderDTO {
    /** 订单ID（必填） */
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;

    /** 快递公司名称（必填） */
    @NotBlank(message = "快递公司不能为空")
    private String courier;
}
