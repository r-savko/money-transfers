package com.revolut.money.transfer.db.transaction;

import org.apache.ibatis.session.SqlSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.util.Producer;

public class TransactionManager {

    private static final Logger log = LogManager.getLogger(TransactionManager.class);

    private SqlSessionManager sqlSessionManager;

    public TransactionManager(SqlSessionManager sqlSessionManager) {
        this.sqlSessionManager = sqlSessionManager;
    }

    public <T> T runInTransaction(Producer<T> actionInTransaction) {
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

    public void runInTransaction(Runnable actionInTransaction) {
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

    private void beforeStart() {
        sqlSessionManager.startManagedSession();
    }

    private void afterEnd() {
        try {
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }

    private void onError() {
        try {
            rollbackTransaction();
        } finally {
            onFinish();
        }
    }

    private void onFinish() {
        sqlSessionManager.close();
    }

    private void rollbackTransaction() {
        sqlSessionManager.rollback();
    }

    private void commitTransaction() {
        sqlSessionManager.commit();
    }


}
