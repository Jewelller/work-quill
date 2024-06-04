package com.qwizery.workquillserverbase.auth;

import lombok.Data;
import lombok.Setter;

/**
 * 用户信息登陆后的信息，会序列化到Jwt的payload
 */
@Data
public class UserLoginInfo {

    private String sessionId; // 会话id，全局唯一
    private Long userId;
    private String nickname; // 昵称
    @Setter
    private String roleId;

    private Long expiredTime; // 过期时间

    public static final class CurrentUserBuilder {

        private UserLoginInfo currentUser;

        private CurrentUserBuilder() {
            currentUser = new UserLoginInfo();
        }

        public static CurrentUserBuilder aCurrentUser() {
            return new CurrentUserBuilder();
        }

        public CurrentUserBuilder sessionId(String sessionId) {
            currentUser.setSessionId(sessionId);
            return this;
        }

        public CurrentUserBuilder userId(Long userId) {
            currentUser.setUserId(userId);
            return this;
        }

        public CurrentUserBuilder nickname(String nickname) {
            currentUser.setNickname(nickname);
            return this;
        }

        public CurrentUserBuilder roleId(String roleId) {
            currentUser.setRoleId(roleId);
            return this;
        }

        public CurrentUserBuilder expiredTime(Long expiredTime) {
            currentUser.setExpiredTime(expiredTime);
            return this;
        }

        public UserLoginInfo build() {
            return currentUser;
        }
    }
}
