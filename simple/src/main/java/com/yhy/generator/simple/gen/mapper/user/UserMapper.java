package com.yhy.generator.simple.gen.mapper.user;

import com.yhy.generator.simple.gen.model.user.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}