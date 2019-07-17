package com.revolut.money.transfer.specification

import com.revolut.money.transfer.model.Account
import com.revolut.money.transfer.model.Transaction
import com.revolut.money.transfer.model.TransactionType
import com.revolut.money.transfer.resource.model.TransferRequest
import com.revolut.money.transfer.util.ClientUtils
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class TransferSpecification extends Specification {

    @Shared
    private def dropwizardSupport

    @Shared
    private def apiUrl

    @Shared
    def client

    def setup() {}

    def cleanup() {}

    def setupSpec() {
        dropwizardSupport = ClientUtils.getDropwizardSupport()
        dropwizardSupport.before()
        client = ClientUtils.getClient(dropwizardSupport)
        apiUrl = ClientUtils.getApiUrl(dropwizardSupport)
    }

    def cleanupSpec() {
        dropwizardSupport.after()
    }

    @Unroll
    def "Transfer funds same currency"() {
        given:
        def accountFrom = 2
        def accountTo = 1
        BigDecimal amount = 12.345
        TransferRequest request = new TransferRequest().setAmount(amount).setFromAccount(accountFrom)
                .setToAccount(accountTo)

        when:
        def transaction = client.target("${apiUrl}/v1/transfer").request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), Transaction)

        def accountToResponse = client.target("${apiUrl}/v1/account/${accountTo}").request().get(Account)
        def accountFromResponse = client.target("${apiUrl}/v1/account/${accountFrom}").request().get(Account)

        then:
        //check transaction
        assert transaction.amount == amount
        assert transaction.type == TransactionType.DEBIT
        assert transaction.account == accountFrom

        //check destination account
        assert accountToResponse.balance == 12.345
        assert accountToResponse.accountId == accountTo

        //check source account
        assert accountFromResponse.balance == 1542.655
        assert accountFromResponse.accountId == accountFrom
    }

    @Unroll
    def "Transfer funds different currency"() {
        given:
        BigDecimal exchangeRate = 1.12632
        def accountFrom = 4
        def accountTo = 1
        BigDecimal amount = 10
        TransferRequest request = new TransferRequest().setAmount(amount).setFromAccount(accountFrom)
                .setToAccount(accountTo)

        def accountToBeforeTransfer = client.target("${apiUrl}/v1/account/${accountTo}").request().get(Account)
        def accountFromBeforeTransfer = client.target("${apiUrl}/v1/account/${accountFrom}").request().get(Account)

        when:
        def transaction = client.target("${apiUrl}/v1/transfer").request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), Transaction)

        def accountToResponse = client.target("${apiUrl}/v1/account/${accountTo}").request().get(Account)
        def accountFromResponse = client.target("${apiUrl}/v1/account/${accountFrom}").request().get(Account)

        then:
        //check transaction
        assert transaction.amount == amount
        assert transaction.type == TransactionType.DEBIT
        assert transaction.account == accountFrom

        //check destination account
        assert accountToResponse.balance == accountToBeforeTransfer.balance + amount * exchangeRate
        assert accountToResponse.accountId == accountTo

        //check source account
        assert accountFromResponse.balance == accountFromBeforeTransfer.balance - amount
        assert accountFromResponse.accountId == accountFrom
    }

}
