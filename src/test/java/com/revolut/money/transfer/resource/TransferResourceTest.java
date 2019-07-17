package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.resource.model.TransferRequest;
import com.revolut.money.transfer.service.TransferService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TransferResourceTest {

    private static final TransferService transferService = mock(TransferService.class);

    private static final String ENDPOINT_TRANSFER = "/v1/transfer";
    private static final Long ACCOUNT_ID_FROM = 1L;
    private static final Long ACCOUNT_ID_TO = 2L;
    private static final Long TRANSACTION_ID = 1L;


    public static final ResourceExtension extension = ResourceExtension.builder()
            .addResource(new TransferResource(transferService))
            .build();

    @AfterEach
    void tearDown() {
        reset(transferService);
    }

    @Test
    void transferTest() {
        // Given
        Transaction transaction = new Transaction().setTransactionId(TRANSACTION_ID).setAmount(BigDecimal.TEN)
                .setTransferDate(new Date()).setMessage("General transaction info");

        TransferRequest request = new TransferRequest().setFromAccount(ACCOUNT_ID_FROM).setToAccount(ACCOUNT_ID_TO)
                .setAmount(BigDecimal.TEN);

        when(transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.TEN)).thenReturn(transaction);

        // When
        Transaction transactionResponse = extension.target(ENDPOINT_TRANSFER).request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), Transaction.class);

        // Then
        assertThat(transactionResponse).isEqualTo(transaction);
        verify(transferService).transfer(eq(ACCOUNT_ID_FROM), eq(ACCOUNT_ID_TO), eq(BigDecimal.TEN));
    }

}
