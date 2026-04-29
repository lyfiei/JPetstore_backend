
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.ItemRequestDTO;
import com.csu.model.vo.ItemVO;
import com.csu.service.ItemService;
import com.csu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;
    private final ProductService productService;
    
    @GetMapping
    public String itemList(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String productId,
                          Model model) {
        model.addAttribute("itemPage", itemService.getItemList(pageNum, pageSize, keyword, productId));
        model.addAttribute("products", productService.getProductsByCategory(null));
        model.addAttribute("keyword", keyword);
        model.addAttribute("productId", productId);
        return "admin/item/list";
    }
    
    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new ItemRequestDTO());
        model.addAttribute("products", productService.getProductsByCategory(null));
        return "admin/item/form";
    }
    
    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable String itemId, Model model) {
        ItemVO item = itemService.getItemDetail(itemId);
        model.addAttribute("item", item);
        return "admin/item/detail";
    }
    
    @GetMapping("/{itemId}/edit")
    public String editItemForm(@PathVariable String itemId, Model model) {
        ItemVO item = itemService.getItemDetail(itemId);
        model.addAttribute("item", item);
        model.addAttribute("products", productService.getProductsByCategory(null));
        model.addAttribute("isEdit", true);
        return "admin/item/form";
    }
    
    @PostMapping
    @ResponseBody
    public Result<Void> addItem(@RequestBody ItemRequestDTO itemDTO) {
        boolean success = itemService.addItem(itemDTO);
        return success ? Result.success() : Result.error("添加货品失败");
    }
    
    @PutMapping("/{itemId}")
    @ResponseBody
    public Result<Void> updateItem(@PathVariable String itemId,
                                   @RequestBody ItemRequestDTO itemDTO) {
        boolean success = itemService.updateItem(itemId, itemDTO);
        return success ? Result.success() : Result.error("更新货品失败");
    }
    
    @DeleteMapping("/{itemId}")
    @ResponseBody
    public Result<Void> deleteItem(@PathVariable String itemId) {
        boolean success = itemService.deleteItem(itemId);
        return success ? Result.success() : Result.error("删除货品失败");
    }
}
