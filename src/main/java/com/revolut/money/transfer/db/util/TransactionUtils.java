package com.revolut.money.transfer.db.util;

import org.apache.ibatis.session.SqlSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.util.Producer;

public final class TransactionUtils {

    private static final Logger log = LogManager.getLogger(TransactionUtils.class);

    private static SqlSessionManager sqlSessionManager;

    public static <T> T runInTransaction(Producer<T> actionInTransaction) {
        beforeStart();
        T result;
        try {
            result = actionInTransaction.call();
        } catch (Exception e) {
            onError();
            log.error("Unable to execute logic in transaction", e);
            throw e;
        }
        afterEnd();
        return result;
    }

    public static void runInTransaction(Runnable actionInTransaction) {
        beforeStart();
        try {
            actionInTransaction.run();
        } catch (Exception e) {
            onError();
            log.error("Unable to execute logic in transaction", e);
            throw e;
        }
        afterEnd();
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
