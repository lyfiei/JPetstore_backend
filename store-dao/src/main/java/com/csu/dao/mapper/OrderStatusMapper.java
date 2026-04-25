package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 订单状态历史数据访问接口
 */
@Mapper
public interface OrderStatusMapper extends BaseMapper<OrderStatus> {

    /**
     * 根据订单ID查询状态历史记录
     */
    List<OrderStatus> selectByOrderId(@Param("orderId") int orderId);

    /**
     * 插入订单状态记录
     */
    int insertOrderStatus(@Param("orderStatus") OrderStatus orderStatus);

    /**
     * 批量插入订单状态记录
     */
    int batchInsertOrderStatus(@Param("orderStatusList") List<OrderStatus> orderStatusList);

    /**
     * 查询最新的订单状态
     */
    OrderStatus selectLatestStatus(@Param("orderId") int orderId);

    /**
     * 查询特定状态的订单ID列表
     */
    List<Integer> selectOrderIdsByStatus(@Param("status") String status);

    /**
     * 统计某时间段内的状态变更次数
     */
    int countStatusChanges(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
