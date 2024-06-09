package com.qwizery.work_quill.server.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qwizery.work_quill.server.service.mapper.DepartmentMapper;
import com.qwizery.work_quill.server.service.entity.Department;
import com.qwizery.work_quill.server.service.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}

