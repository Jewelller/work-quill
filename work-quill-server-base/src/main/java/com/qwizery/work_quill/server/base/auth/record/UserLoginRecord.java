package com.qwizery.work_quill.server.base.auth.record;

import com.qwizery.work_quill.server.base.entity.User;

import java.util.List;

/**
 * 用于内部传输不带密码的 {@link User}
 **/
public record UserLoginRecord(Long userId, String username, String nickname) {

    public UserLoginInfoRecord getUserLoginInfoRecord(List<String> roles, Long expireTime) {
        return new UserLoginInfoRecord(userId, username, nickname, roles, expireTime);
    }

}
