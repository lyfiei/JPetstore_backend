
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.CategoryRequestDTO;
import com.csu.model.vo.CategoryVO;
import com.csu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public String categoryList(@RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(required = false) String keyword,
                              Model model) {
        model.addAttribute("categoryPage", categoryService.getCategoryList(pageNum, pageSize, keyword));
        model.addAttribute("keyword", keyword);
        return "admin/category/list";
    }
    
    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new CategoryRequestDTO());
        return "admin/category/form";
    }
    
    @GetMapping("/{categoryId}")
    public String categoryDetail(@PathVariable String categoryId, Model model) {
        CategoryVO category = categoryService.getCategoryDetail(categoryId);
        model.addAttribute("category", category);
        return "admin/category/detail";
    }
    
    @GetMapping("/{categoryId}/edit")
    public String editCategoryForm(@PathVariable String categoryId, Model model) {
        CategoryVO category = categoryService.getCategoryDetail(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("isEdit", true);
        return "admin/category/form";
    }
    
    @PostMapping
    @ResponseBody
    public Result<Void> addCategory(@RequestBody CategoryRequestDTO categoryDTO) {
        boolean success = categoryService.addCategory(categoryDTO);
        return success ? Result.success() : Result.error("添加分类失败");
    }
    
    @PutMapping("/{categoryId}")
    @ResponseBody
    public Result<Void> updateCategory(@PathVariable String categoryId,
                                       @RequestBody CategoryRequestDTO categoryDTO) {
        boolean success = categoryService.updateCategory(categoryId, categoryDTO);
        return success ? Result.success() : Result.error("更新分类失败");
    }
    
    @DeleteMapping("/{categoryId}")
    @ResponseBody
    public Result<Void> deleteCategory(@PathVariable String categoryId) {
        boolean success = categoryService.deleteCategory(categoryId);
        return success ? Result.success() : Result.error("删除分类失败");
    }
}
