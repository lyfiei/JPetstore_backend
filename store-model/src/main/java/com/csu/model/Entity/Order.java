package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("orders")
public class Order {
    @TableId("orderid")
    private int orderId;
    @TableField("userid")
    private String userId;
    @TableField("orderdate")
    private Date orderDate;
    @TableField("shipaddr1")
    private String shipAddress1;
    @TableField("shipaddr2")
    private String shipAddress2;
    @TableField("shipcity")
    private String shipCity;
    @TableField("shipstate")
    private String shipState;
    @TableField("shipzip")
    private String shipZip;
    @TableField("shipcountry")
    private String shipCountry;
    @TableField("billaddr1")
    private String billAddress1;
    @TableField("billaddr2")
    private String billAddress2;
    @TableField("billcity")
    private String billCity;
    @TableField("billstate")
    private String billState;
    @TableField("billzip")
    private String billZip;
    @TableField("billcountry")
    private String billCountry;
    private String courier;
    @TableField("totalprice")
    private BigDecimal totalPrice;
    @TableField("billtofirstname")
    private String billToFirstName;
    @TableField("billtolastname")
    private String billToLastName;
    @TableField("shiptofirstname")
    private String shipToFirstName;
    @TableField("shiptolastname")
    private String shipToLastName;
    @TableField("creditcard")
    private String creditCard;
    @TableField("exprdate")
    private String expiryDate;
    @TableField("cardtype")
    private String cardType;
    private String locale;
    // 以下字段不在 orders 表中，通过业务层组装
    @TableField(exist = false)
    private String status;
    @TableField(exist = false)
    private List<LineItem> lineItems = new ArrayList<LineItem>();
}
