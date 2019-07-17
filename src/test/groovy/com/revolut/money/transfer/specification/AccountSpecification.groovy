package com.revolut.money.transfer.specification

import com.revolut.money.transfer.model.Account
import com.revolut.money.transfer.model.Transaction
import com.revolut.money.transfer.util.ClientUtils
import org.apache.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class AccountSpecification extends Specification {

    @Shared
    private dropwizardSupport

    @Shared
    private apiUrl

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
    def "Get account with id = #accountId"(accountId, status, userId) {

        when:
        Response response = client.target("${apiUrl}/v1/account/${accountId}").request().get()

        then:
        def account = response.readEntity(Account.class)
        assert response.getStatus() == status
        if (status == HttpStatus.SC_OK) {
            assert account.userId == userId
        }

        where:
        accountId || status                  | userId
        1         || HttpStatus.SC_OK        | 1
        2         || HttpStatus.SC_OK        | 1
        4         || HttpStatus.SC_OK        | 3
        6         || HttpStatus.SC_NOT_FOUND | null

    }

    @Unroll
    def "Get transactions for user with id = #accountId"(accountId, status, size) {

        when:
        Response response = client.target("${apiUrl}/v1/account/${accountId}/transactions").request().get()

        then:
        def transactions = response.readEntity(new GenericType<List<Transaction>>() {})
        assert response.getStatus() == status
        if (status == HttpStatus.SC_OK) {
            assert transactions.size() == size
        }

        where:
        accountId || status           | size
        1         || HttpStatus.SC_OK | 2
        2         || HttpStatus.SC_OK | 0

    }

    @Unroll
    def "Delete account"() {
        given:
        def accountId = 1

        when:
        client.target("${apiUrl}/v1/account/${accountId}").request().delete()
        def accountResponse = client.target("${apiUrl}/v1/account/${accountId}").request().get()
        def transactionsResponse = client.target("${apiUrl}/v1/account/${accountId}/transactions").request().get()

        then:
        def transactions = transactionsResponse.readEntity(new GenericType<List<Transaction>>() {})
        assert accountResponse.status == HttpStatus.SC_NOT_FOUND
        assert transactionsResponse.status == HttpStatus.SC_OK
        assert transactions.size() == 0

    }
}
