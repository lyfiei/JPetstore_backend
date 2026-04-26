
package com.csu.admin.controller;

import com.csu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    
    private final OrderService orderService;
    
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalOrders", orderService.getTotalOrderCount());
        model.addAttribute("pendingOrders", orderService.getPendingOrderCount());
        return "admin/dashboard";
    }
    
    @GetMapping("/admin")
    public String index() {
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/admin/dashboard";
    }
}
