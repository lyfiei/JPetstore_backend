package com.csu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.AccountMapper;
import com.csu.dao.mapper.CategoryMapper;
import com.csu.dao.mapper.ItemMapper;
import com.csu.dao.mapper.LineItemMapper;
import com.csu.dao.mapper.OrderMapper;
import com.csu.dao.mapper.OrderStatusMapper;
import com.csu.dao.mapper.ProductMapper;
import com.csu.model.DTO.OrderStatusUpdateDTO;
import com.csu.model.Entity.Account;
import com.csu.model.Entity.Category;
import com.csu.model.Entity.Item;
import com.csu.model.Entity.LineItem;
import com.csu.model.Entity.Order;
import com.csu.model.Entity.OrderStatus;
import com.csu.model.Entity.Product;
import com.csu.model.Enum.OrderStatusEnum;
import com.csu.model.vo.ItemVO;
import com.csu.model.vo.LineItemVO;
import com.csu.model.vo.OrderStatusHistoryVO;
import com.csu.model.vo.OrderVO;
import com.csu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final LineItemMapper lineItemMapper;
    private final ItemMapper itemMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final AccountMapper accountMapper;

    @Override
    public Page<OrderVO> getOrderList(int pageNum, int pageSize, String keyword, String status) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(status)) {
            // 需要通过orderstatus表查询最新状态
            // 这里简化处理，实际可能需要自定义SQL
        }
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Order::getOrderId, keyword)
                       .or()
                       .like(Order::getUserId, keyword);
        }
        
        queryWrapper.orderByDesc(Order::getOrderDate);
        
        Page<Order> orderPage = orderMapper.selectPage(page, queryWrapper);
        
        // 转换为VO
        Page<OrderVO> voPage = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        List<OrderVO> voList = orderPage.getRecords().stream()
                .map(this::convertToListVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public OrderVO getOrderDetail(int orderId) {
        Order order = orderMapper.selectWithDetails(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        OrderVO vo = convertToDetailVO(order);
        
        // 获取订单项
        List<LineItem> lineItems = lineItemMapper.selectByOrderId(orderId);
        List<LineItemVO> lineItemVOs = lineItems.stream()
                .map(this::convertLineItemToVO)
                .collect(Collectors.toList());
        vo.setLineItems(lineItemVOs);
        
        // 获取状态历史
        List<OrderStatus> statusHistory = orderStatusMapper.selectByOrderId(orderId);
        List<OrderStatusHistoryVO> historyVOs = statusHistory.stream()
                .map(this::convertStatusHistoryToVO)
                .collect(Collectors.toList());
        vo.setStatusHistory(historyVOs);
        
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderStatus(OrderStatusUpdateDTO statusDTO) {
        Order order = orderMapper.selectById(statusDTO.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 验证状态值
        try {
            OrderStatusEnum.fromCode(statusDTO.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("无效的订单状态");
        }
        
        // 更新订单状态
        boolean success = orderMapper.updateOrderStatus(statusDTO.getOrderId(), statusDTO.getStatus()) > 0;
        
        // 记录状态历史
        if (success) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderId(statusDTO.getOrderId());
            orderStatus.setStatus(statusDTO.getStatus());
            orderStatus.setTimestamp(new Date());
            
            // 获取当前最大的lineNumber
            List<OrderStatus> existingStatuses = orderStatusMapper.selectByOrderId(statusDTO.getOrderId());
            int maxLineNumber = existingStatuses.stream()
                    .mapToInt(OrderStatus::getLineNumber)
                    .max()
                    .orElse(0);
            orderStatus.setLineNumber(maxLineNumber + 1);
            
            orderStatusMapper.insertOrderStatus(orderStatus);
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean shipOrder(int orderId, String courier) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 更新快递信息
        boolean success = orderMapper.updateCourier(orderId, courier) > 0;
        
        // 更新状态为已发货
        if (success) {
            success = orderMapper.updateOrderStatus(orderId, OrderStatusEnum.SHIPPED.getCode()) > 0;
            
            // 记录状态历史
            if (success) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setOrderId(orderId);
                orderStatus.setStatus(OrderStatusEnum.SHIPPED.getCode());
                orderStatus.setTimestamp(new Date());
                
                List<OrderStatus> existingStatuses = orderStatusMapper.selectByOrderId(orderId);
                int maxLineNumber = existingStatuses.stream()
                        .mapToInt(OrderStatus::getLineNumber)
                        .max()
                        .orElse(0);
                orderStatus.setLineNumber(maxLineNumber + 1);
                
                orderStatusMapper.insertOrderStatus(orderStatus);
            }
        }
        
        return success;
    }

    @Override
    public int getTotalOrderCount() {
        Long count = orderMapper.selectCount(null);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public int getPendingOrderCount() {
        return orderMapper.countOrdersByStatus(OrderStatusEnum.PENDING.getCode());
    }

    /**
     * 将Entity转换为列表VO
     */
    private OrderVO convertToListVO(Order order) {
        OrderVO vo = new OrderVO();
        vo.setOrderId(order.getOrderId());
        vo.setUserId(order.getUserId());
        vo.setOrderDate(order.getOrderDate());
        vo.setTotalPrice(order.getTotalPrice());
        
        // 获取客户姓名
        Account account = accountMapper.selectByUsername(order.getUserId());
        if (account != null) {
            vo.setCustomerName(account.getFirstName() + " " + account.getLastName());
        }
        
        // 获取最新状态
        OrderStatus latestStatus = orderStatusMapper.selectLatestStatus(order.getOrderId());
        if (latestStatus != null) {
            vo.setStatus(latestStatus.getStatus());
            try {
                OrderStatusEnum statusEnum = OrderStatusEnum.fromCode(latestStatus.getStatus());
                vo.setStatusDescription(statusEnum.getDescription());
            } catch (IllegalArgumentException e) {
                vo.setStatusDescription("未知状态");
            }
        }
        
        return vo;
    }

    /**
     * 将Entity转换为详情VO
     */
    private OrderVO convertToDetailVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        
        // 获取客户姓名
        Account account = accountMapper.selectByUsername(order.getUserId());
        if (account != null) {
            vo.setCustomerName(account.getFirstName() + " " + account.getLastName());
        }
        
        // 设置状态描述
        if (StringUtils.hasText(order.getStatus())) {
            try {
                OrderStatusEnum statusEnum = OrderStatusEnum.fromCode(order.getStatus());
                vo.setStatusDescription(statusEnum.getDescription());
            } catch (IllegalArgumentException e) {
                vo.setStatusDescription("未知状态");
            }
        }
        
        return vo;
    }

    /**
     * 将LineItem转换为VO
     */
    private LineItemVO convertLineItemToVO(LineItem lineItem) {
        LineItemVO vo = new LineItemVO();
        BeanUtils.copyProperties(lineItem, vo);
        
        // 获取货品信息
        Item item = itemMapper.selectById(lineItem.getItemId());
        if (item != null) {
            ItemVO itemVO = new ItemVO();
            itemVO.setItemId(item.getItemId());
            itemVO.setListPrice(item.getListPrice());
            itemVO.setUnitCost(item.getUnitCost());
            itemVO.setAttribute1(item.getAttribute1());
            itemVO.setAttribute2(item.getAttribute2());
            
            // 获取产品信息
            Product product = productMapper.selectById(item.getProductId());
            if (product != null) {
                itemVO.setProductName(product.getName());
                itemVO.setProductId(product.getProductId());
                
                // 获取分类信息
                Category category = categoryMapper.selectById(product.getCategoryId());
                if (category != null) {
                    itemVO.setCategoryName(category.getName());
                }
            }
            
            vo.setItem(itemVO);
        }
        
        return vo;
    }

    /**
     * 将OrderStatus转换为VO
     */
    private OrderStatusHistoryVO convertStatusHistoryToVO(OrderStatus orderStatus) {
        OrderStatusHistoryVO vo = new OrderStatusHistoryVO();
        BeanUtils.copyProperties(orderStatus, vo);
        
        try {
            OrderStatusEnum statusEnum = OrderStatusEnum.fromCode(orderStatus.getStatus());
            vo.setStatusDescription(statusEnum.getDescription());
        } catch (IllegalArgumentException e) {
            vo.setStatusDescription("未知状态");
        }
        
        return vo;
    }
}
