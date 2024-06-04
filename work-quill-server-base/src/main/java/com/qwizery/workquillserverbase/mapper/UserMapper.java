package com.qwizery.workquillserverbase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qwizery.workquillserverbase.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
