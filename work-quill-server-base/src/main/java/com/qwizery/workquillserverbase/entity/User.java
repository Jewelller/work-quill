package com.qwizery.workquillserverbase.entity;

import com.qwizery.workquillserverbase.auth.record.UserLoginRecord;
import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private String nickname;
    private String password;
    private boolean enabled;

    public UserLoginRecord getUserLoginRecord() {
        return new UserLoginRecord(userId, username, nickname);
    }
}
