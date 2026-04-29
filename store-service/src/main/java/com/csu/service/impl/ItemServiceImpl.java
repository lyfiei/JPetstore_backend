package com.csu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.CategoryMapper;
import com.csu.dao.mapper.ItemMapper;
import com.csu.dao.mapper.ItemQuantityMapper;
import com.csu.dao.mapper.ProductMapper;
import com.csu.model.DTO.ItemRequestDTO;
import com.csu.model.Entity.Category;
import com.csu.model.Entity.Item;
import com.csu.model.Entity.ItemQuantity;
import com.csu.model.Entity.Product;
import com.csu.model.Enum.ItemStatusEnum;
import com.csu.model.vo.ItemVO;
import com.csu.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 货品服务实现类
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemQuantityMapper itemQuantityMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<ItemVO> getItemList(int pageNum, int pageSize, String keyword, String productId) {
        Page<Item> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(productId)) {
            queryWrapper.eq(Item::getProductId, productId);
        }
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Item::getItemId, keyword)
                       .or()
                       .like(Item::getAttribute1, keyword)
                       .or()
                       .like(Item::getAttribute2, keyword);
        }
        
        queryWrapper.orderByAsc(Item::getItemId);
        
        Page<Item> itemPage = itemMapper.selectPage(page, queryWrapper);
        
        // 转换为VO
        Page<ItemVO> voPage = new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<ItemVO> voList = itemPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public ItemVO getItemDetail(String itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new BusinessException("货品不存在");
        }
        return convertToVO(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addItem(ItemRequestDTO itemDTO) {
        // 检查货品ID是否已存在
        if (itemMapper.countByItemId(itemDTO.getItemId()) > 0) {
            throw new BusinessException("货品ID已存在");
        }
        
        // 检查产品是否存在
        Product product = productMapper.selectById(itemDTO.getProductId());
        if (product == null) {
            throw new BusinessException("产品不存在");
        }
        
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        
        boolean success = itemMapper.insert(item) > 0;
        
        // 初始化库存
        if (success && itemDTO.getQuantity() != null) {
            ItemQuantity quantity = new ItemQuantity();
            quantity.setItemId(itemDTO.getItemId());
            quantity.setQuantity(itemDTO.getQuantity());
            itemQuantityMapper.insert(quantity);
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateItem(String itemId, ItemRequestDTO itemDTO) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new BusinessException("货品不存在");
        }
        
        // 检查产品是否存在
        Product product = productMapper.selectById(itemDTO.getProductId());
        if (product == null) {
            throw new BusinessException("产品不存在");
        }
        
        BeanUtils.copyProperties(itemDTO, item);
        item.setItemId(itemId); // 确保ID不被修改
        
        boolean success = itemMapper.updateById(item) > 0;
        
        // 更新库存
        if (success && itemDTO.getQuantity() != null) {
            ItemQuantity quantity = itemQuantityMapper.selectByItemId(itemId);
            if (quantity == null) {
                quantity = new ItemQuantity();
                quantity.setItemId(itemId);
                quantity.setQuantity(itemDTO.getQuantity());
                itemQuantityMapper.insert(quantity);
            } else {
                itemQuantityMapper.updateQuantity(itemId, itemDTO.getQuantity());
            }
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteItem(String itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new BusinessException("货品不存在");
        }
        
        // 删除库存记录
        itemQuantityMapper.deleteById(itemId);
        
        return itemMapper.deleteById(itemId) > 0;
    }

    /**
     * 将Entity转换为VO
     */
    private ItemVO convertToVO(Item item) {
        ItemVO vo = new ItemVO();
        vo.setItemId(item.getItemId());
        vo.setProductId(item.getProductId());
        vo.setListPrice(item.getListPrice());
        vo.setUnitCost(item.getUnitCost());
        vo.setSupplierId(item.getSupplierId());
        vo.setStatus(item.getStatus());
        vo.setAttribute1(item.getAttribute1());
        vo.setAttribute2(item.getAttribute2());
        vo.setAttribute3(item.getAttribute3());
        vo.setAttribute4(item.getAttribute4());
        vo.setAttribute5(item.getAttribute5());
        
        // 设置状态描述
        try {
            ItemStatusEnum statusEnum = ItemStatusEnum.fromCode(item.getStatus());
            vo.setStatusDescription(statusEnum.getDescription());
        } catch (IllegalArgumentException e) {
            vo.setStatusDescription("未知状态");
        }
        
        // 获取产品信息
        if (StringUtils.hasText(item.getProductId())) {
            Product product = productMapper.selectById(item.getProductId());
            if (product != null) {
                vo.setProductName(product.getName());
                vo.setCategoryId(product.getCategoryId());
                
                // 获取分类信息
                if (StringUtils.hasText(product.getCategoryId())) {
                    Category category = categoryMapper.selectById(product.getCategoryId());
                    if (category != null) {
                        vo.setCategoryName(category.getName());
                    }
                }
            }
        }
        
        // 获取库存数量
        ItemQuantity quantity = itemQuantityMapper.selectByItemId(item.getItemId());
        if (quantity != null) {
            vo.setQuantity(quantity.getQuantity());
        } else {
            vo.setQuantity(0);
        }
        
        return vo;
    }
}
