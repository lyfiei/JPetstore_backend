
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.OrderStatusUpdateDTO;
import com.csu.model.vo.OrderVO;
import com.csu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping
    public String orderList(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "10") int pageSize,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String status,
                           Model model) {
        model.addAttribute("orderPage", orderService.getOrderList(pageNum, pageSize, keyword, status));
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "admin/order/list";
    }
    
    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable int orderId, Model model) {
        OrderVO order = orderService.getOrderDetail(orderId);
        model.addAttribute("order", order);
        return "admin/order/detail";
    }
    
    @PostMapping("/{orderId}/status")
    @ResponseBody
    public Result<Void> updateStatus(@PathVariable int orderId,
                                     @RequestBody OrderStatusUpdateDTO statusDTO) {
        statusDTO.setOrderId(orderId);
        boolean success = orderService.updateOrderStatus(statusDTO);
        return success ? Result.success() : Result.error("更新订单状态失败");
    }
    
    @PostMapping("/{orderId}/ship")
    @ResponseBody
    public Result<Void> shipOrder(@PathVariable int orderId,
                                  @RequestParam String courier) {
        boolean success = orderService.shipOrder(orderId, courier);
        return success ? Result.success() : Result.error("发货失败");
    }
}
