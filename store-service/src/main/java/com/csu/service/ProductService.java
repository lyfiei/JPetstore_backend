package com.csu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.model.DTO.ProductRequestDTO;
import com.csu.model.vo.ProductVO;

import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService {

    /**
     * 分页获取商品列表
     */
    Page<ProductVO> getProductList(int pageNum, int pageSize, String keyword, String categoryId);

    /**
     * 根据分类ID获取商品列表
     */
    List<ProductVO> getProductsByCategory(String categoryId);

    /**
     * 获取商品详情
     */
    ProductVO getProductDetail(String productId);

    /**
     * 添加商品
     */
    boolean addProduct(ProductRequestDTO productDTO);

    /**
     * 更新商品
     */
    boolean updateProduct(String productId, ProductRequestDTO productDTO);

    /**
     * 删除商品
     */
    boolean deleteProduct(String productId);
}
