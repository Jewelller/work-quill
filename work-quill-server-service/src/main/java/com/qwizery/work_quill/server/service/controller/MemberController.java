package com.qwizery.work_quill.server.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qwizery.work_quill.component.controller.BaseController;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.service.entity.Member;
import com.qwizery.work_quill.server.service.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("${app.base.api.prefix}/member")
public class MemberController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MemberService memberService;

    private <T> Result success(T data) {
        return success("op.success", data);
    }

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param member 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<Member> page, Member member) {
        return success(this.memberService.page(page, new QueryWrapper<>(member)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return success(this.memberService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param member 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Member member) {
        return success(this.memberService.save(member));
    }

    /**
     * 修改数据
     *
     * @param member 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Member member) {
        return success(this.memberService.updateById(member));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return success(this.memberService.removeByIds(idList));
    }
}

