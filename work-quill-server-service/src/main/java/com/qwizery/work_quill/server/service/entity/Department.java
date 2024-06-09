package com.qwizery.work_quill.server.service.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Department extends Model<Department> {

    private Long departmentId;

    private String departmentName;

    private Integer enabled;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.departmentId;
    }
}

