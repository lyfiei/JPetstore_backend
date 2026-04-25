package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单数据访问接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据用户ID查询订单列表
     */
    List<Order> selectByUserId(@Param("userId") String userId);

    /**
     * 根据订单ID查询订单详情
     */
    Order selectWithDetails(@Param("orderId") int orderId);

    /**
     * 条件查询订单列表（支持多条件筛选）
     */
    List<Order> selectOrdersByCondition(
            @Param("orderId") Integer orderId,
            @Param("userId") String userId,
            @Param("status") String status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    /**
     * 统计符合条件的订单数量
     */
    int countOrdersByCondition(
            @Param("orderId") Integer orderId,
            @Param("userId") String userId,
            @Param("status") String status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    /**
     * 查询今日订单列表
     */
    List<Order> selectTodayOrders();

    /**
     * 查询待处理订单列表（状态为P）
     */
    List<Order> selectPendingOrders();

    /**
     * 更新订单快递信息
     */
    int updateCourier(@Param("orderId") int orderId, @Param("courier") String courier);

    /**
     * 更新订单状态
     */
    int updateOrderStatus(@Param("orderId") int orderId, @Param("status") String status);

    /**
     * 删除订单
     */
    int deleteOrder(@Param("orderId") int orderId);

    /**
     * 统计订单总金额（按状态）
     */
    BigDecimal sumTotalPriceByStatus(@Param("status") String status);

    /**
     * 统计订单数量（按状态）
     */
    int countOrdersByStatus(@Param("status") String status);

    /**
     * 查询订单统计数据
     */
    Map<String, Object> selectOrderStatistics();
}
