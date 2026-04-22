package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orderstatus")
public class OrderStatus {
    @TableId("orderid")
    private int orderId;
    @TableField("linenum")
    private int lineNumber;
    @TableField("timestamp")
    private Date timestamp;
    @TableField("status")
    private String status;
}
