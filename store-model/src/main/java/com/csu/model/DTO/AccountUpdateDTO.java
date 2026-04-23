package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 账户信息更新请求数据传输对象（DTO）
 * 用于后台管理系统修改用户信息时接收请求参数
 */
@Data
public class AccountUpdateDTO {
    /** 用户名（必填，唯一标识，不可修改） */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 邮箱地址（必填，需符合邮箱格式） */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /** 名字（必填） */
    @NotBlank(message = "名字不能为空")
    private String firstName;

    /** 姓氏（必填） */
    @NotBlank(message = "姓氏不能为空")
    private String lastName;

    /** 电话号码（可选） */
    private String phone;
    
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;

    /** 偏好分类ID（可选） */
    private String favouriteCategoryId;
    
    /** 语言偏好（可选） */
    private String languagePreference;
    
    /** 是否显示列表选项（可选） */
    private Boolean listOption;
    
    /** 是否显示横幅广告（可选） */
    private Boolean bannerOption;
}
