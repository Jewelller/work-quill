package com.qwizery.work_quill.server.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.qwizery.work_quill.server.base.auth.record.UserLoginRecord;
import lombok.Data;

@Data
public class User {
    @TableId
    private Long userId;
    private String username;
    private String nickname;
    private String password;
    private boolean enabled;

    public UserLoginRecord getUserLoginRecord() {
        return new UserLoginRecord(userId, username, nickname);
    }

    public static User of(Long userId, String username) {
        var user = new User();
        user.userId = userId;
        user.username = username;
        user.enabled = true;
        return user;
    }

    public static User ofId(Long userId) {
        return of(userId, null);
    }

    public static User ofUsername(String username) {
        return of(null, username);
    }

}
