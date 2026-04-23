package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求数据传输对象（DTO）
 * 用于接收用户登录时提交的用户名和密码
 */
@Data
public class LoginDTO {
    /** 用户名（必填） */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 密码（必填） */
    @NotBlank(message = "密码不能为空")
    private String password;
}
