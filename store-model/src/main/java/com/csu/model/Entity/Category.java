package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data //如果只希望有getter和setter方法，可以使用@Getter和@Setter注解。这里要全部生成所以用Data
@TableName("category") //与数据库表名映射
public class Category {
    @TableId("catid") //与数据库表的主键字段映射
    private String categoryId;
    //@TableField("name")  与数据库表字段映射,这里与数据库表字段名称一致，所以可以不写
    private String name;
    @TableField("descn") //与数据库表字段映射
    private String description;
}
