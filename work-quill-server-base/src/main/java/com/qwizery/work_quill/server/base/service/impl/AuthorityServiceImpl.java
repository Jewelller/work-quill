package com.qwizery.work_quill.server.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qwizery.work_quill.server.base.mapper.AuthorityMapper;
import com.qwizery.work_quill.server.base.entity.Authority;
import com.qwizery.work_quill.server.base.service.AuthorityService;
import org.springframework.stereotype.Service;

/**
 * (Authority)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 02:13:48
 */
@Service("authorityService")
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}

