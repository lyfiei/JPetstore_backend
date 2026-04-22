package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId("productid")
    private String productId;  //这个在数据库会被认为是product_id,所以需要映射一下
    @TableField("category")
    private String categoryId;
    private String name;
    @TableField("descn")
    private String description;
}
