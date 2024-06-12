package com.qwizery.work_quill.server.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Department extends Model<Department> {

    @TableId(type = IdType.AUTO)
    private Long departmentId;

    private String departmentName;

    @TableLogic(value = "1", delval = "0")
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

