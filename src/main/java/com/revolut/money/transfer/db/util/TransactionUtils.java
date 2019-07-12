package com.revolut.money.transfer.db.util;

import org.apache.ibatis.session.SqlSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.util.Producer;

import java.util.Optional;

public final class TransactionUtils {

    private static final Logger log = LogManager.getLogger(TransactionUtils.class);

    private static SqlSessionManager sqlSessionManager;

    public static <T> Optional<T> runInTransaction(Producer<T> producer) {
        beforeStart();
        T result;
        try {
            result = producer.call();
        } catch (Exception e) {
            onError();
            log.error("Unable to execute logic in transaction", e);
            return Optional.empty();
        }
        afterEnd();
        return Optional.of(result);

    }

    private static void beforeStart() {
        sqlSessionManager = SessionFactoryUtils.getSqlSessionManager();
        sqlSessionManager.startManagedSession();
    }

    private static void afterEnd() {
        try {
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }

    private static void onError() {
        try {
            rollbackTransaction();
        } finally {
            onFinish();
        }
    }

    private static void onFinish() {
        sqlSessionManager.close();
    }

    private static void rollbackTransaction() {
        sqlSessionManager.rollback();
    }

    private static void commitTransaction() {
        sqlSessionManager.commit();
    }


}
