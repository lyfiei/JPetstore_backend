package com.csu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.CategoryMapper;
import com.csu.model.DTO.CategoryRequestDTO;
import com.csu.model.Entity.Category;
import com.csu.model.vo.CategoryVO;
import com.csu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类服务实现类
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryVO> getCategoryList(int pageNum, int pageSize, String keyword) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Category::getName, keyword)
                       .or()
                       .like(Category::getDescription, keyword);
        }
        
        queryWrapper.orderByAsc(Category::getCategoryId);
        
        Page<Category> categoryPage = categoryMapper.selectPage(page, queryWrapper);
        
        // 转换为VO
        Page<CategoryVO> voPage = new Page<>(categoryPage.getCurrent(), categoryPage.getSize(), categoryPage.getTotal());
        List<CategoryVO> voList = categoryPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        List<Category> categories = categoryMapper.selectAllCategories();
        return categories.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryVO getCategoryDetail(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return convertToVO(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCategory(CategoryRequestDTO categoryDTO) {
        // 检查分类ID是否已存在
        if (categoryMapper.countByCategoryId(categoryDTO.getCategoryId()) > 0) {
            throw new BusinessException("分类ID已存在");
        }
        
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        
        return categoryMapper.insert(category) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCategory(String categoryId, CategoryRequestDTO categoryDTO) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCategoryId(categoryId); // 确保ID不被修改
        
        return categoryMapper.updateById(category) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCategory(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        
        return categoryMapper.deleteById(categoryId) > 0;
    }

    /**
     * 将Entity转换为VO
     */
    private CategoryVO convertToVO(Category category) {
        CategoryVO vo = new CategoryVO();
        vo.setCategoryId(category.getCategoryId());
        vo.setCategoryName(category.getName());
        vo.setDescription(category.getDescription());
        return vo;
    }
}
