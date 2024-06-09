package com.qwizery.work_quill.server.service.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class Salary extends Model<Salary> {

    private Long salaryId;

    private Long memberId;

    private String amount;

    private Date timestamp;

    private Integer enabled;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.salaryId;
    }
}

