package com.revolut.money.transfer.specification

import com.revolut.money.transfer.model.User
import com.revolut.money.transfer.util.ClientUtils
import org.apache.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.Response

class UserSpecification extends Specification {

    private static final DW_SUPPORT = ClientUtils.DROPWIZARD_SUPPORT

    @Shared
    def client

    def setup() {}

    def cleanup() {}

    def setupSpec() {
        DW_SUPPORT.before()
        client = ClientUtils.client
    }

    def cleanupSpec() {
        DW_SUPPORT.after()
    }

    @Unroll
    def "Get user with id = #userId"(userId, status, name, surname) {

        when:
        Response response = client.target("http://localhost:${DW_SUPPORT.getLocalPort()}/v1/user/${userId}")
                .request()
                .get()

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

}
