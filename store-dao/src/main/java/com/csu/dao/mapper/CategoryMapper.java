package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类数据访问接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有分类列表
     */
    List<Category> selectAllCategories();

    /**
     * 根据分类名称模糊查询
     */
    List<Category> searchByName(@Param("name") String name);

    /**
     * 检查分类ID是否存在
     */
    int countByCategoryId(@Param("categoryId") String categoryId);
}
