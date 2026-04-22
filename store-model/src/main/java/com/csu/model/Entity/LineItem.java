package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("lineitem")
public class LineItem {
    @TableId("orderid")
    private int orderId;
    @TableField("linenum")
    private int lineNumber;
    @TableField("quantity")
    private int quantity;
    @TableField("itemid")
    private String itemId;
    @TableField("unitprice")
    private BigDecimal unitPrice;
}
