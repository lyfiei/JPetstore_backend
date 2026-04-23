package com.csu.model.DTO;

import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品请求数据传输对象（DTO）
 * 用于后台管理系统新增或修改商品（SKU）时接收请求参数
 */
@Data
public class ItemRequestDTO {
    /** 商品ID/SKU（必填，唯一标识） */
    @NotBlank(message = "商品ID不能为空")
    private String itemId;

    /** 产品ID（必填，关联到product表） */
    @NotBlank(message = "产品ID不能为空")
    private String productId;

    /** 销售价格（必填，必须大于0） */
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal listPrice;

    /** 成本价格（必填，必须大于0） */
    @NotNull(message = "成本价不能为空")
    @DecimalMin(value = "0.01", message = "成本价必须大于0")
    private BigDecimal unitCost;

    /** 供应商ID（必填，关联到supplier表） */
    @NotNull(message = "供应商ID不能为空")
    private Integer supplierId;

    /** 商品状态（必填，P=可售/O=缺货/D=已下架） */
    @NotBlank(message = "商品状态不能为空")
    private String status;

    /** 商品属性1（如颜色） */
    private String attribute1;
    /** 商品属性2（如尺寸） */
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    @Min(value = 0, message = "库存数量不能为负数")
    private Integer quantity;
}
