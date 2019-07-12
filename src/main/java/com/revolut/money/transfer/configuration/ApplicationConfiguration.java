package com.revolut.money.transfer.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabase() {
        return database;
    }

    @JsonProperty("database")
    public ApplicationConfiguration setDatabase(DataSourceFactory database) {
        this.database = database;
        return this;
    }
}
