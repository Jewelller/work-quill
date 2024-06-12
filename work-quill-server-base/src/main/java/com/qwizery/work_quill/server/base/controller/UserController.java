package com.qwizery.work_quill.server.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qwizery.work_quill.component.model.Result;
import com.qwizery.work_quill.server.base.entity.User;
import com.qwizery.work_quill.server.base.service.UserService;
import com.qwizery.work_quill.server.base.util.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base.api.prefix}/user")
public class UserController extends AuthorizedController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    private <T> Result success(T data) {
        return success("op.success", data);
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @PostMapping("/select-all")
    public Result selectAll(Page<User> page, User user) {
        if (hasRole("ROLE_ADMIN")) {
            var data = this.userService.page(page, new QueryWrapper<>(user));
            data.setRecords(data.getRecords().stream().peek(u -> u.setPassword(null)).toList());
            return success(data);
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
            var data = this.userService.getById(id);
            data.setPassword(null);
            return success(data);
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
        User user = JSON.parse((String) request.getAttribute("requestBody"), User.class);
        log.info(user.toString());

        if (hasRole("ROLE_ADMIN")) {
            User tempUser = userService.getOne(Wrappers.<User>query().eq("username", user.getUsername()));
            if (Optional.ofNullable(tempUser).isPresent()) {
                log.info(tempUser.toString());
                return error("error.username_duplicate");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (userService.save(user)) {
                return Result.success("op.success");
            }
            return Result.error("op.fail");
        }
        return Result.error("error.no_access");
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result update(HttpServletRequest request) {
        User user = JSON.parse((String) request.getAttribute("requestBody"), User.class);

        if (hasRole("ROLE_ADMIN")) {
            var updatedUser = userService.getById(user.getUserId());
            if (Optional.ofNullable(updatedUser).isPresent()) {
                updatedUser.setUsername(user.getUsername());
                updatedUser.setPassword(user.getPassword() == null ? updatedUser.getPassword() : passwordEncoder.encode(user.getPassword()));
            }

            if (this.userService.updateById(updatedUser)) {
                return Result.success("op.success");
            }
            return error("op.fail");
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
            return success(this.userService.removeByIds(idList));
        }
        return error("error.no_access");
    }
}

