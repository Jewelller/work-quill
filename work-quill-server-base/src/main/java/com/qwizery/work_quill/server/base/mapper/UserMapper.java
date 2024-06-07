package com.qwizery.work_quill.server.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qwizery.work_quill.server.base.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
