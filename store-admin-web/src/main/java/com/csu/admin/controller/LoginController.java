
package com.csu.admin.controller;

import com.csu.common.result.Result;
import com.csu.model.DTO.LoginDTO;
import com.csu.service.LoginService;
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
    
    private final LoginService loginService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/api/login")
    @ResponseBody
    public Result<Void> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        try {
            boolean success = loginService.login(loginDTO);
            if (success) {
                session.setAttribute("adminUser", loginDTO.getUsername());
                return Result.success();
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        
        return Result.error("用户名或密码错误");
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
