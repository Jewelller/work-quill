package com.qwizery.work_quill.server.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.service.entity.Department;
import com.qwizery.work_quill.server.service.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base.api.prefix}/department")
public class DepartmentController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private DepartmentService departmentService;

    private <T> Result success(T data) {
        return success("op.success", data);
    }

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param department 查询实体
     * @return 所有数据
     */
    @PostMapping("/select-all")
    public Result selectAll(Page<Department> page, Department department) {
        return success(this.departmentService.page(page, new QueryWrapper<>(department)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return success(this.departmentService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param department 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Department department) {
        if (Optional.ofNullable(departmentService.getOne(Wrappers.<Department>query()
                .eq("department_name", department.getDepartmentName()
                ))).isPresent()) {
            return error("error.department_name_duplicate");
        }
        if (this.departmentService.save(department)) {
            return success("op.success");
        }
        return error("op.fail");
    }

    /**
     * 修改数据
     *
     * @param department 实体对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody Department department) {
        return success(this.departmentService.updateById(department));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return success(this.departmentService.removeByIds(idList));
    }
}

