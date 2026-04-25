package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品数据访问接口
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 根据分类ID查询产品列表
     */
    List<Product> selectByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 根据产品名称模糊查询
     */
    List<Product> searchByName(@Param("name") String name);

    /**
     * 查询某个分类下的产品数量
     */
    int countByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 检查产品ID是否存在
     */
    int countByProductId(@Param("productId") String productId);

    /**
     * 关联查询产品信息（包含分类名称）
     */
    List<Product> selectWithCategoryName(@Param("categoryId") String categoryId);
}
