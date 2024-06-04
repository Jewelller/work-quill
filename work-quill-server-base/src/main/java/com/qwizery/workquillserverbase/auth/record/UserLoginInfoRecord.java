package com.qwizery.workquillserverbase.auth.record;

import java.util.List;

public record UserLoginInfoRecord(
        Long userId, String username, String nickname, List<String> roles, Long expireTime
) {
}
