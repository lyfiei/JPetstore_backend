package com.csu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.model.DTO.OrderStatusUpdateDTO;
import com.csu.model.vo.OrderVO;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 分页获取订单列表
     */
    Page<OrderVO> getOrderList(int pageNum, int pageSize, String keyword, String status);

    /**
     * 获取订单详情
     */
    OrderVO getOrderDetail(int orderId);

    /**
     * 更新订单状态
     */
    boolean updateOrderStatus(OrderStatusUpdateDTO statusDTO);

    /**
     * 发货
     */
    boolean shipOrder(int orderId, String courier);

    /**
     * 获取订单总数
     */
    int getTotalOrderCount();

    /**
     * 获取待处理订单数
     */
    int getPendingOrderCount();
}
