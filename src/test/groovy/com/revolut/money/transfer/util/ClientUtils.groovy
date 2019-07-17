package com.revolut.money.transfer.util

import com.revolut.money.transfer.MoneyTransferApplication
import com.revolut.money.transfer.configuration.ApplicationConfiguration
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.util.Duration

class ClientUtils {


    static def getDropwizardSupport() {
        String tempFile = File.createTempFile("test-example", null).getAbsolutePath();
        return new DropwizardTestSupport<ApplicationConfiguration>(MoneyTransferApplication.class,
                ResourceHelpers.resourceFilePath("test-application.yaml"),
                ConfigOverride.config("database.url", "jdbc:h2:${tempFile}")
        )
    }

    static def getClient(dropwizardSupport) {
        JerseyClientConfiguration clientConfiguration = new JerseyClientConfiguration()
        clientConfiguration.timeout = Duration.seconds(10)
        return new JerseyClientBuilder(dropwizardSupport.getEnvironment()).using(clientConfiguration)
                .build("Money Transfer Test Client")
    }

    static def getApiUrl(dropwizardSupport) {
        return "http://localhost:${dropwizardSupport.getLocalPort()}"
    }

}
