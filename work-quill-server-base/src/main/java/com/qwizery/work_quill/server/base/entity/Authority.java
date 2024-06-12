package com.qwizery.work_quill.server.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Authority extends Model<Authority> {

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String role;

    public static Authority ofId(Long userId) {
        Authority authority = new Authority();
        authority.setUserId(userId);
        return authority;
    }
}

