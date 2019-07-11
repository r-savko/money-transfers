package com.revolut.money.transfer.db.mapper;

import org.apache.ibatis.annotations.Select;

public interface PingMapper {

    String SELECT_PING = "SELECT 1 AS health";

    @Select(SELECT_PING)
    Integer ping();

}
