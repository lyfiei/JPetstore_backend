package com.csu.model.vo;

import lombok.Data;

/**
 * 用户列表视图对象（VO）
 * 用于后台管理系统展示用户列表摘要信息
 */
@Data
public class UserListVO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    
    /** 账户状态码（OK/OFF） */
    private String status;
    
    /** 账户状态描述（正常/禁用） */
    private String statusDescription;
}
