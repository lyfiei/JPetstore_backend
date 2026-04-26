
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.LoginDTO;
import com.csu.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    
    private final AccountService accountService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/api/login")
    @ResponseBody
    public Result<Void> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        // TODO: 实现登录逻辑，验证用户名密码
        // 这里简化处理，实际应该查询数据库并验证密码
        
        if ("admin".equals(loginDTO.getUsername()) && "admin".equals(loginDTO.getPassword())) {
            session.setAttribute("adminUser", loginDTO.getUsername());
            return Result.success();
        }
        
        return Result.error("用户名或密码错误");
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
