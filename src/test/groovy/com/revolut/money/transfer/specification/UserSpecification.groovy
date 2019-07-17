package com.revolut.money.transfer.specification

import com.revolut.money.transfer.model.Account
import com.revolut.money.transfer.model.User
import com.revolut.money.transfer.resource.model.CreateAccountRequest
import com.revolut.money.transfer.util.ClientUtils
import org.apache.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.client.Entity
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class UserSpecification extends Specification {

    @Shared
    private dropwizardSupport

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
    def "Get user with id = #userId"(userId, status, name, surname) {

        when:
        Response response = client.target("${apiUrl}/v1/user/${userId}").request().get()

        then:
        def user = response.readEntity(User.class)
        assert response.getStatus() == status
        if (status == HttpStatus.SC_OK) {
            assert user.name == name
            assert user.surname == surname
        }

        where:
        userId || status                  | name    | surname
        1      || HttpStatus.SC_OK        | 'Otto'  | 'Octavius'
        2      || HttpStatus.SC_OK        | 'Bruce' | 'Banner'
        4      || HttpStatus.SC_NOT_FOUND | null    | null

    }

    @Unroll
    def "Get accounts for user with id = #userId"(userId, status, size, balances) {

        when:
        Response response = client.target("${apiUrl}/v1/user/${userId}/accounts").request().get()

        then:
        def accounts = response.readEntity(new GenericType<List<Account>>() {})
        assert response.getStatus() == status
        if (status == HttpStatus.SC_OK) {
            assert accounts.size() == size
            assert accounts.collect { it.balance }.intersect(balances).size() == size
        }

        where:
        userId || status           | size | balances
        1      || HttpStatus.SC_OK | 2    | [0, 1555]
        2      || HttpStatus.SC_OK | 1    | [2500]
        4      || HttpStatus.SC_OK | 0    | []

    }

    @Unroll
    def "Create user account"() {
        given:
        def userId = 1
        CreateAccountRequest request = new CreateAccountRequest()
                .setAccountNumber("INTEGRATION TEST").setCurrencyCode("EUR")

        when:
        def account = client.target("${apiUrl}/v1/user/${userId}/account").request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), Account.class)

        def accounts = client.target("${apiUrl}/v1/user/${userId}/accounts").request()
                .get(new GenericType<List<Account>>() {})

        then:
        assert account.balance == BigDecimal.ZERO
        assert accounts.size() == 3
        assert accounts.findAll { it.accountNumber == 'INTEGRATION TEST' }.size() == 1

    }

}
