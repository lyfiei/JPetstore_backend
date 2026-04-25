package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.ItemQuantity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存数据访问接口
 */
@Mapper
public interface ItemQuantityMapper extends BaseMapper<ItemQuantity> {

    /**
     * 根据商品ID查询库存
     */
    ItemQuantity selectByItemId(@Param("itemId") String itemId);

    /**
     * 更新商品库存数量
     */
    int updateQuantity(@Param("itemId") String itemId, @Param("quantity") int quantity);

    /**
     * 增加库存数量
     */
    int increaseQuantity(@Param("itemId") String itemId, @Param("amount") int amount);

    /**
     * 减少库存数量
     */
    int decreaseQuantity(@Param("itemId") String itemId, @Param("amount") int amount);

    /**
     * 批量查询库存
     */
    List<ItemQuantity> selectByItemIds(@Param("itemIds") List<String> itemIds);

    /**
     * 查询低库存商品（库存小于指定值）
     */
    List<Map<String, Object>> selectLowStockItems(@Param("threshold") int threshold);

    /**
     * 检查库存是否充足
     */
    int checkStockSufficient(@Param("itemId") String itemId, @Param("requiredQuantity") int requiredQuantity);
}
