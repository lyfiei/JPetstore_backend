package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 密码重置请求数据传输对象（DTO）
 * 用于后台管理系统重置用户密码时接收请求参数
 */
@Data
public class PasswordResetDTO {
    /** 用户名（必填） */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 新密码（必填，至少6位） */
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "密码长度至少为6位")
    private String newPassword;

    /** 确认密码（必填，需与新密码一致） */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
