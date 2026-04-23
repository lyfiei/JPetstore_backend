package com.csu.model.DTO;

import lombok.Data;
import java.util.Date;

/**
 * 订单查询请求数据传输对象（DTO）
 * 用于后台管理系统查询订单时接收筛选条件和分页参数
 */
@Data
public class OrderQueryDTO {
    /** 订单ID（可选，精确查询） */
    private Integer orderId;
    
    /** 用户ID（可选，按用户查询） */
    private String userId;
    
    /** 订单状态（可选，P/A/R/S） */
    private String status;
    
    /** 开始日期（可选，按日期范围查询） */
    private Date startDate;
    
    /** 结束日期（可选，按日期范围查询） */
    private Date endDate;

    /** 页码（默认第1页） */
    private Integer pageNum = 1;
    
    /** 每页数量（默认10条） */
    private Integer pageSize = 10;
}
