package com.csu.model.VO;

import lombok.Data;
import java.util.Date;

@Data
public class OrderStatusHistoryVO {
    private int orderId;
    private int lineNumber;
    private Date timestamp;
    private String status;
    private String statusDescription;
}
