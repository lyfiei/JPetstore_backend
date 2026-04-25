package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品（SKU）数据访问接口
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 根据产品ID查询商品列表
     */
    List<Item> selectByProductId(@Param("productId") String productId);

    /**
     * 根据关键字搜索商品（模糊搜索产品名称和属性）
     */
    List<Item> searchItems(@Param("keyword") String keyword);

    /**
     * 查询可售商品列表（状态为P）
     */
    List<Item> selectAvailableItems();

    /**
     * 根据状态查询商品列表
     */
    List<Item> selectByStatus(@Param("status") String status);

    /**
     * 更新商品价格
     */
    int updatePrice(@Param("itemId") String itemId, @Param("listPrice") BigDecimal listPrice, @Param("unitCost") BigDecimal unitCost);

    /**
     * 更新商品状态
     */
    int updateStatus(@Param("itemId") String itemId, @Param("status") String status);

    /**
     * 批量更新商品状态
     */
    int batchUpdateStatus(@Param("itemIds") List<String> itemIds, @Param("status") String status);

    /**
     * 检查商品ID是否存在
     */
    int countByItemId(@Param("itemId") String itemId);

    /**
     * 关联查询商品信息（包含产品名称和分类名称）
     */
    List<Map<String, Object>> selectItemWithProductAndCategory(@Param("productId") String productId);
}
