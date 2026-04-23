package com.csu.model.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息视图对象（VO）
 * 用于后台管理系统展示商品详细信息，包含关联的产品和分类信息
 */
@Data
public class ItemVO {
    private String itemId;

    private String productId;
    private String productName;
    private String categoryId;
    private String categoryName;
    
    /** 商品描述图片HTML（用于前端展示） */
    private String descriptionImage;
    
    /** 商品描述文本 */
    private String descriptionText;

    private BigDecimal listPrice; //售货价格
    private BigDecimal unitCost; //成本价格
    
    /** 供应商ID */
    private int supplierId;
    
    /** 商品状态码（P/O/D） */
    private String status;
    
    /** 商品状态描述（可售/缺货/已下架） */
    private String statusDescription;

    /** 属性1（如颜色、尺寸等） */
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    /** 库存数量 */
    private Integer quantity;
}
