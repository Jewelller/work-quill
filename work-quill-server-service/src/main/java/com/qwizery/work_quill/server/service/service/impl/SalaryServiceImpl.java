package com.qwizery.work_quill.server.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qwizery.work_quill.server.service.mapper.SalaryMapper;
import com.qwizery.work_quill.server.service.entity.Salary;
import com.qwizery.work_quill.server.service.service.SalaryService;
import org.springframework.stereotype.Service;

@Service("salaryService")
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

}

