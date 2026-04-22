package com.csu.model.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("signon") // 必须对应数据库表名
public class SignOn {
    @TableId("username") // 对应数据库主键名
    private String username;
    private String password;
}
