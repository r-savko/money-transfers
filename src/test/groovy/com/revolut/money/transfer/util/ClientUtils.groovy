package com.revolut.money.transfer.util

import com.revolut.money.transfer.MoneyTransferApplication
import com.revolut.money.transfer.configuration.ApplicationConfiguration
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers

class ClientUtils {

    private static final def TMP_FILE = createTempFile()

    static def createTempFile() {
        return File.createTempFile("test-example", null).getAbsolutePath()
    }

    public static def DROPWIZARD_SUPPORT =
            new DropwizardTestSupport<ApplicationConfiguration>(MoneyTransferApplication.class,
                    ResourceHelpers.resourceFilePath("test-application.yaml"),
                    ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE)
            )

    static def getClient() {
        return new JerseyClientBuilder(DROPWIZARD_SUPPORT.getEnvironment())
                .build("Money Transfer Test Client")
    }

}
