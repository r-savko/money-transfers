package com.revolut.money.transfer.db.util;

import com.revolut.money.transfer.db.mapper.PingMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public final class SessionFactoryUtils {

    private static SqlSessionManager sqlSessionManager;

    private SessionFactoryUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void initSessionManager(DataSource dataSource) {
        TransactionFactory trxFactory = new JdbcTransactionFactory();
        Environment env = new Environment("prod", trxFactory, dataSource);
        Configuration config = new Configuration(env);
        config.addMappers(PingMapper.class.getPackage().getName());
        sqlSessionManager = SqlSessionManager.newInstance(new SqlSessionFactoryBuilder().build(config));
    }

    public static SqlSessionManager getSqlSessionManager() {
        if (null != sqlSessionManager) {
            return sqlSessionManager;
        } else {
            throw new IllegalStateException("Not initialized sqlSessionFactory");
        }
    }
}
