package com.qwizery.work_quill.server.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("authority")
public class Role {
    @TableId
    private Long userId;
    private String role;

    public static Role ofId(Long userId) {
        Role role = new Role();
        role.setUserId(userId);
        return role;
    }
}
