package com.csu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.model.DTO.ItemRequestDTO;
import com.csu.model.vo.ItemVO;

import java.util.List;

/**
 * 货品服务接口
 */
public interface ItemService {

    /**
     * 分页获取货品列表
     */
    Page<ItemVO> getItemList(int pageNum, int pageSize, String keyword, String productId);

    /**
     * 获取货品详情
     */
    ItemVO getItemDetail(String itemId);

    /**
     * 添加货品
     */
    boolean addItem(ItemRequestDTO itemDTO);

    /**
     * 更新货品
     */
    boolean updateItem(String itemId, ItemRequestDTO itemDTO);

    /**
     * 删除货品
     */
    boolean deleteItem(String itemId);
}
