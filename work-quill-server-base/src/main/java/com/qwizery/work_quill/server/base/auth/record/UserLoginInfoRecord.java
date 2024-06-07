package com.qwizery.work_quill.server.base.auth.record;

import java.util.List;

/**
 * 用于JWT传输用户信息
 **/
public record UserLoginInfoRecord(
        Long userId, String username, String nickname, List<String> roles, Long expireTime
) {
}
