package com.revolut.money.transfer.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.revolut.money.transfer.db.mapper.PingMapper;
import com.revolut.money.transfer.db.util.SessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

public class DatabaseHealthCheck extends HealthCheck {

    @Override
    protected Result check() {
        try (SqlSession session = SessionFactoryUtils.getSqlSessionManager().openSession()) {
            return session.getMapper(PingMapper.class).ping().equals(1)
                    ? Result.healthy()
                    : Result.unhealthy("Unhealthy Database");
        } catch (Exception e) {
            return Result.unhealthy(e);
        }
    }

}