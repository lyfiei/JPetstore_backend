package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.VO.AccountVO;
import com.csu.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    
    private final AccountService accountService;
    
    @GetMapping
    public String userList(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) String keyword,
                          Model model) {
        model.addAttribute("userPage", accountService.getUserList(pageNum, pageSize, keyword));
        model.addAttribute("keyword", keyword);
        return "admin/user/list";
    }
    
    @GetMapping("/{username}")
    public String userDetail(@PathVariable String username, Model model) {
        AccountVO user = accountService.getUserDetail(username);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }
    
    @PostMapping("/{username}/status")
    @ResponseBody
    public Result<Void> updateStatus(@PathVariable String username, 
                                     @RequestParam String status) {
        boolean success = accountService.updateUserStatus(username, status);
        return success ? Result.success() : Result.error("更新用户状态失败");
    }
    
    @DeleteMapping("/{username}")
    @ResponseBody
    public Result<Void> deleteUser(@PathVariable String username) {
        boolean success = accountService.deleteUser(username);
        return success ? Result.success() : Result.error("删除用户失败");
    }
}
