package com.qwizery.workquillserverbase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("authority")
public class Role {
    private Long userId;
    private String role;
}
