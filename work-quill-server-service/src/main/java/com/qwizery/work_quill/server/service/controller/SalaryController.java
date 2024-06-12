package com.qwizery.work_quill.server.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.service.entity.Salary;
import com.qwizery.work_quill.server.service.service.SalaryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("${app.base.api.prefix}/salary")
public class SalaryController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SalaryService salaryService;

    private <T> Result success(T data) {
        return success("op.success", data);
    }

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param salary 查询实体
     * @return 所有数据
     */
    @PostMapping("/select-all")
    public Result selectAll(Page<Salary> page, Salary salary) {
        return success(this.salaryService.page(page, new QueryWrapper<>(salary)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return success(this.salaryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param salary 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Salary salary) {
        return success(this.salaryService.save(salary));
    }

    /**
     * 修改数据
     *
     * @param salary 实体对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody Salary salary) {
        return success(this.salaryService.updateById(salary));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return success(this.salaryService.removeByIds(idList));
    }
}

