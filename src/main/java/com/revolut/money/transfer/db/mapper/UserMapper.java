package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.db.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    String SELECT_BY_ID = "SELECT user_id, name, surname FROM user WHERE user_id = #{id}";

    Long persist(User config);

    @Select(SELECT_BY_ID)
    User find(Long id);

    Long update(User user);

    Long delete(Long id);

}
