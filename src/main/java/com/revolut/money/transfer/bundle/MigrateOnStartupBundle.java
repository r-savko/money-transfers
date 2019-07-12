package com.revolut.money.transfer.bundle;

import com.revolut.money.transfer.configuration.ApplicationConfiguration;
import com.revolut.money.transfer.db.util.DbMigrationConstants;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class MigrateOnStartupBundle implements ConfiguredBundle<ApplicationConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(MigrateOnStartupBundle.class);

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {
        migrateDatabase(configuration, environment);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    private void migrateDatabase(ApplicationConfiguration configuration, Environment environment) {
        LOG.info("Start database migration");
        ManagedDataSource dataSource = createMigrationDataSource(configuration, environment);
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection conn = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
            new Liquibase(DbMigrationConstants.MIGRATIONS_FILE, new ClassLoaderResourceAccessor(), database).update("");
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to migrate database", ex);
        } finally {
            try {
                dataSource.stop();
            } catch (Exception e) {
                LOG.warn("Unable to stop data source.");
            }
        }
        LOG.info("Database migration has been completed successfully");
    }

    private ManagedDataSource createMigrationDataSource(ApplicationConfiguration configuration, Environment environment) {
        DataSourceFactory dataSourceFactory = configuration.getDatabase();
        return dataSourceFactory.build(environment.metrics(), "migrationDataSource");
    }
}
