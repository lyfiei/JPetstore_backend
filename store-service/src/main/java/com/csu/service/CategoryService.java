package com.csu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.model.DTO.CategoryRequestDTO;
import com.csu.model.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    /**
     * 分页获取分类列表
     */
    Page<CategoryVO> getCategoryList(int pageNum, int pageSize, String keyword);

    /**
     * 获取所有分类
     */
    List<CategoryVO> getAllCategories();

    /**
     * 获取分类详情
     */
    CategoryVO getCategoryDetail(String categoryId);

    /**
     * 添加分类
     */
    boolean addCategory(CategoryRequestDTO categoryDTO);

    /**
     * 更新分类
     */
    boolean updateCategory(String categoryId, CategoryRequestDTO categoryDTO);

    /**
     * 删除分类
     */
    boolean deleteCategory(String categoryId);
}
