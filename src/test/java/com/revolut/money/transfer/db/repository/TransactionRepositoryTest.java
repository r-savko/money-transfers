package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.TransactionMapper;
import com.revolut.money.transfer.model.Transaction;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    private static final Long TRANSACTION_ID = 1L;
    private static final Transaction TRANSACTION = new Transaction().setTransactionId(TRANSACTION_ID).setAmount(BigDecimal.TEN)
            .setTransferDate(new Date()).setMessage("General transaction info");

    private TransactionMapper transactionMapper;
    private TransactionRepository transactionRepository;
    private SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);

    @BeforeEach
    void setup() {
        transactionRepository = new TransactionRepository(sqlSessionManager);
        transactionMapper = mock(TransactionMapper.class);
        when(sqlSessionManager.getMapper(TransactionMapper.class)).thenReturn(transactionMapper);
    }

    @Test
    void createTest() {
        // When
        transactionRepository.create(TRANSACTION);

        // Then
        verify(transactionMapper).create(eq(TRANSACTION));
    }

    @Test
    void readTest() {
        // When
        transactionRepository.read(TRANSACTION_ID);

        // Then
        verify(transactionMapper).read(TRANSACTION_ID);
    }

    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> transactionRepository.update(TRANSACTION));
    }

    @Test
    void deleteTest() {
        // When
        transactionRepository.delete(TRANSACTION_ID);

        // Then
        verify(transactionMapper).delete(TRANSACTION_ID);
    }

    @Test
    void deleteAccountTransactionsTest() {
        // When
        transactionRepository.deleteAccountTransactions(TRANSACTION_ID);

        // Then
        verify(transactionMapper).deleteAccountTransactions(TRANSACTION_ID);
    }

    @Test
    void readAccountTransactionsTest() {
        // When
        transactionRepository.readAccountTransactions(TRANSACTION_ID);

        // Then
        verify(transactionMapper).readAccountTransactions(TRANSACTION_ID);
    }

}
