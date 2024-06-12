package com.qwizery.work_quill.server.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Member extends Model<Member> {

    @TableId(type = IdType.AUTO)
    private Long memberId;

    private String name;

    private Long departmentId;

    private String gender;

    private Date birthday;

    private String eduBackground;

    private String phone;

    private String email;

    private String memberAddress;

    @TableLogic(value = "1", delval = "0")
    private Integer enabled;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.memberId;
    }
}

