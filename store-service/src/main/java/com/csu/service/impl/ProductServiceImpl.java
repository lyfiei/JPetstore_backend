package com.csu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.CategoryMapper;
import com.csu.dao.mapper.ProductMapper;
import com.csu.model.DTO.ProductRequestDTO;
import com.csu.model.Entity.Category;
import com.csu.model.Entity.Product;
import com.csu.model.vo.ProductVO;
import com.csu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<ProductVO> getProductList(int pageNum, int pageSize, String keyword, String categoryId) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(categoryId)) {
            queryWrapper.eq(Product::getCategoryId, categoryId);
        }
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Product::getName, keyword)
                       .or()
                       .like(Product::getDescription, keyword);
        }
        
        queryWrapper.orderByAsc(Product::getProductId);
        
        Page<Product> productPage = productMapper.selectPage(page, queryWrapper);
        
        // 转换为VO
        Page<ProductVO> voPage = new Page<>(productPage.getCurrent(), productPage.getSize(), productPage.getTotal());
        List<ProductVO> voList = productPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public List<ProductVO> getProductsByCategory(String categoryId) {
        List<Product> products;
        if (StringUtils.hasText(categoryId)) {
            products = productMapper.selectByCategoryId(categoryId);
        } else {
            products = productMapper.selectList(null);
        }
        return products.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVO getProductDetail(String productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        return convertToVO(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProduct(ProductRequestDTO productDTO) {
        // 检查产品ID是否已存在
        if (productMapper.countByProductId(productDTO.getProductId()) > 0) {
            throw new BusinessException("商品ID已存在");
        }
        
        // 检查分类是否存在
        if (categoryMapper.countByCategoryId(productDTO.getCategoryId()) == 0) {
            throw new BusinessException("分类不存在");
        }
        
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        
        return productMapper.insert(product) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProduct(String productId, ProductRequestDTO productDTO) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查分类是否存在
        if (categoryMapper.countByCategoryId(productDTO.getCategoryId()) == 0) {
            throw new BusinessException("分类不存在");
        }
        
        BeanUtils.copyProperties(productDTO, product);
        product.setProductId(productId); // 确保ID不被修改
        
        return productMapper.updateById(product) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProduct(String productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        return productMapper.deleteById(productId) > 0;
    }

    /**
     * 将Entity转换为VO
     */
    private ProductVO convertToVO(Product product) {
        ProductVO vo = new ProductVO();
        vo.setProductId(product.getProductId());
        vo.setCategoryId(product.getCategoryId());
        vo.setName(product.getName());
        vo.setDescription(product.getDescription());
        
        // 获取分类名称
        if (StringUtils.hasText(product.getCategoryId())) {
            Category category = categoryMapper.selectById(product.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }
        
        return vo;
    }
}
