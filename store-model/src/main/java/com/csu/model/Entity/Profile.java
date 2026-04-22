package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profile")
public class Profile {
    @TableId("userid")
    private String userId;
    @TableField(value = "favcategory")
    private String favouriteCategoryId;
    @TableField(value = "langpref")    // 数据库是 langpref
    private String languagePreference;
    @TableField(value = "mylistopt")   // 数据库是 mylistopt
    private Integer listOption;
    @TableField(value = "banneropt")   // 数据库是 banneropt
    private Integer bannerOption;
}
