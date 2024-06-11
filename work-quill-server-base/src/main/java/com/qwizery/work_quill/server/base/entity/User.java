package com.qwizery.work_quill.server.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.qwizery.work_quill.server.base.auth.record.UserLoginRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String username;

    private String nickname;

    private String password;

    @TableLogic(value = "1", delval = "0")
    private Integer enabled;

    @Override
    public Serializable pkVal() {
        return this.userId;
    }

    public static User of(Long userId, String username) {
        User user = new User();
        user.userId = userId;
        user.username = username;
        return user;
    }

    public UserLoginRecord getUserLoginRecord() {
        return new UserLoginRecord(userId, username, nickname);
    }
}

