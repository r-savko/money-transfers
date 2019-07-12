package com.revolut.money.transfer.db.util;

import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.jersey.internal.util.Producer;

import java.util.function.Predicate;

public final class TransactionUtils {

    private static SqlSessionManager sqlSessionManager;

    public static  <T> T runInTransaction(Producer<T> producer) {
        beforeStart();
        T result;
        try {
            result = producer.call();
        } catch (Exception e) {
            onError();
            throw new RuntimeException("Unable to execute logic in transaction", e);
        }
        afterEnd();
        return result;

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

    public static final void onException(Predicate runnable){

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
