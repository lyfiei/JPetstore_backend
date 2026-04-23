package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户配置信息实体类
 * 对应数据库表：profile
 * 存储用户的个性化设置信息
 */
@Data
@TableName("profile")
public class Profile {
    /** 用户ID（主键，关联account表的userid） */
    @TableId("userid")
    private String userId;
    
    /** 偏好的商品分类ID */
    @TableField(value = "favcategory")
    private String favouriteCategoryId;
    
    /** 语言偏好设置 */
    @TableField(value = "langpref")
    private String languagePreference;
    
    /** 是否显示列表选项（0或1） */
    @TableField(value = "mylistopt")
    private boolean listOption;
    
    /** 是否显示横幅广告（0或1） */
    @TableField(value = "banneropt")
    private boolean bannerOption;
}
