package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.LineItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单项数据访问接口
 */
@Mapper
public interface LineItemMapper extends BaseMapper<LineItem> {

    /**
     * 根据订单ID查询订单项列表
     */
    List<LineItem> selectByOrderId(@Param("orderId") int orderId);

    /**
     * 批量插入订单项
     */
    int batchInsertLineItems(@Param("lineItems") List<LineItem> lineItems);

    /**
     * 根据商品ID查询被订购记录
     */
    List<LineItem> selectByItemId(@Param("itemId") String itemId);

    /**
     * 统计商品销售数量
     */
    int sumQuantityByItemId(@Param("itemId") String itemId);

    /**
     * 查询热销商品排行
     */
    List<Map<String, Object>> selectTopSellingItems(@Param("limit") int limit);
}
