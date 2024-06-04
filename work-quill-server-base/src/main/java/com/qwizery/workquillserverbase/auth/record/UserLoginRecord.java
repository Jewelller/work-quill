package com.qwizery.workquillserverbase.auth.record;

import java.util.List;

public record UserLoginRecord(Long userId, String username, String nickname) {

    public UserLoginInfoRecord getUserLoginInfoRecord(List<String> roles, Long expireTime) {
        return new UserLoginInfoRecord(userId, username, nickname, roles, expireTime);
    }

}
