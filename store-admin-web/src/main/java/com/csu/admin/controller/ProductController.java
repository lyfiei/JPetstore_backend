
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.ProductRequestDTO;
import com.csu.model.vo.ProductVO;
import com.csu.service.CategoryService;
import com.csu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    @GetMapping
    public String productList(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String categoryId,
                             Model model) {
        model.addAttribute("productPage", productService.getProductList(pageNum, pageSize, keyword, categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        return "admin/product/list";
    }
    
    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new ProductRequestDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product/form";
    }
    
    @GetMapping("/{productId}")
    public String productDetail(@PathVariable String productId, Model model) {
        ProductVO product = productService.getProductDetail(productId);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }
    
    @GetMapping("/{productId}/edit")
    public String editProductForm(@PathVariable String productId, Model model) {
        ProductVO product = productService.getProductDetail(productId);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("isEdit", true);
        return "admin/product/form";
    }
    
    @PostMapping
    @ResponseBody
    public Result<Void> addProduct(@RequestBody ProductRequestDTO productDTO) {
        boolean success = productService.addProduct(productDTO);
        return success ? Result.success() : Result.error("添加商品失败");
    }
    
    @PutMapping("/{productId}")
    @ResponseBody
    public Result<Void> updateProduct(@PathVariable String productId,
                                      @RequestBody ProductRequestDTO productDTO) {
        boolean success = productService.updateProduct(productId, productDTO);
        return success ? Result.success() : Result.error("更新商品失败");
    }
    
    @DeleteMapping("/{productId}")
    @ResponseBody
    public Result<Void> deleteProduct(@PathVariable String productId) {
        boolean success = productService.deleteProduct(productId);
        return success ? Result.success() : Result.error("删除商品失败");
    }
}
