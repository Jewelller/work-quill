package com.qwizery.work_quill.server.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qwizery.work_quill.server.base.mapper.UserMapper;
import com.qwizery.work_quill.server.base.entity.User;
import com.qwizery.work_quill.server.base.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 02:13:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

