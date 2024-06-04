package com.qwizery.workquillserverbase.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private String role;
    private boolean enabled;
}
