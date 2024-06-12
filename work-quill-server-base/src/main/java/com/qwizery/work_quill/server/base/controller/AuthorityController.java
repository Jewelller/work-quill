package com.qwizery.work_quill.server.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.base.entity.Authority;
import com.qwizery.work_quill.server.base.service.AuthorityService;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base.api.prefix}/authority")
public class AuthorityController extends AuthorizedController {
    /**
     * 服务对象
     */
    @Resource
    private AuthorityService authorityService;

    private <T> Result success(T data) {
        return success("op.success", data);
    }

    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param authority 查询实体
     * @return 所有数据
     */
    @PostMapping("/select-all")
    public Result selectAll(Page<Authority> page, Authority authority) {
        if (hasRole("ROLE_ADMIN")) {
            return success(this.authorityService.page(page, new QueryWrapper<>(authority)));
        }
        return error("error.no_access");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        if (hasRole("ROLE_ADMIN")) {
            return success(this.authorityService.getById(id));
        }
        return error("error.no_access");
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public Result insert(HttpServletRequest request) {
        var authority = JSON.parse((String) request.getAttribute("requestBody"), Authority.class);

        if (hasRole("ROLE_ADMIN")) {
            if (Optional.ofNullable(authorityService.query()
                    .eq("user_id", authority.getUserId())
                    .eq("role", authority.getRole())).isPresent()) {
                return error("error.role_duplicate");
            }

            return success(this.authorityService.save(authority));
        }
        return error("error.no_access");
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result update(HttpServletRequest request) {
        var authority = JSON.parse((String) request.getAttribute("requestBody"), Authority.class);

        if (hasRole("ROLE_ADMIN")) {
            return success(this.authorityService.updateById(authority));
        }
        return error("error.no_access");
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        if (hasRole("ROLE_ADMIN")) {
            return success(this.authorityService.removeByIds(idList));
        }
        return error("error.no_access");
    }
}

