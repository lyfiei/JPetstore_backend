package com.csu.model.vo;

import lombok.Data;

/**
 * 账户信息视图对象（VO）
 * 用于后台管理系统展示用户详细信息
 */
@Data
public class AccountVO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    
    /** 账户状态码（OK/OFF） */
    private String status;
    
    /** 账户状态描述（正常/禁用） */
    private String statusDescription;
    private String address1;
    private String address2;
    private String city;
    
    /** 州/省 */
    private String state;
    
    /** 邮政编码 */
    private String zip;
    private String country;
    private String phone;

    /** 偏好的分类ID */
    private String favouriteCategoryId;
    
    /** 偏好分类名称 */
    private String favouriteCategoryName;
    
    /** 语言偏好设置 */
    private String languagePreference;
    
    /** 是否显示列表选项 */
    private boolean listOption;
    
    /** 是否显示横幅广告 */
    private boolean bannerOption;
    
    /** 横幅广告名称 */
    private String bannerName;
}
