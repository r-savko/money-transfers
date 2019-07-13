package com.revolut.money.transfer;

import com.revolut.money.transfer.bundle.MigrateOnStartupBundle;
import com.revolut.money.transfer.configuration.ApplicationConfiguration;
import com.revolut.money.transfer.db.repository.*;
import com.revolut.money.transfer.db.util.DbMigrationConstants;
import com.revolut.money.transfer.db.util.SessionFactoryUtils;
import com.revolut.money.transfer.exception.mapper.GenericApplicationExceptionMapper;
import com.revolut.money.transfer.exception.mapper.NotFoundExceptionMapper;
import com.revolut.money.transfer.healthcheck.DatabaseHealthCheck;
import com.revolut.money.transfer.resource.AccountResource;
import com.revolut.money.transfer.resource.TransferResource;
import com.revolut.money.transfer.resource.UserResource;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.TransferService;
import com.revolut.money.transfer.service.UserService;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.ibatis.session.SqlSessionManager;

public class MoneyTransferApplication extends Application<ApplicationConfiguration> {

    private final MigrationsBundle<ApplicationConfiguration> migrationsBundle = new MigrationsBundle<>() {
        @Override
        public DataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration) {
            return configuration.getDatabase();
        }

        @Override
        public String getMigrationsFileName() {
            return DbMigrationConstants.MIGRATIONS_FILE;
        }
    };

    private MigrateOnStartupBundle migrateOnStartupBundle = new MigrateOnStartupBundle();

    public static void main(String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(migrationsBundle);
        bootstrap.addBundle(migrateOnStartupBundle);
        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApplicationConfiguration configuration) {
                // this would be the preferred way to set up swagger, you can also construct the object here programtically if you want
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {

        //Init session factory for mybatis
        ManagedDataSource dataSource = configuration.getDatabase().build(environment.metrics(), "MyBatis-Datasource");
        SessionFactoryUtils.initSessionManager(dataSource);
        SqlSessionManager sessionManager = SessionFactoryUtils.getSqlSessionManager();

        //Init repositories
        UserRepository userRepository = new UserRepository(sessionManager);
        TransactionRepository transactionRepository = new TransactionRepository(sessionManager);
        AccountRepository accountRepository = new AccountRepository(sessionManager);
        ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository(sessionManager);
        CurrencyRepository currencyRepository = new CurrencyRepository(sessionManager);

        //Init services
        UserService userService = new UserService(userRepository);
        AccountService accountService = new AccountService(accountRepository, currencyRepository);
        TransferService transferService = new TransferService(transactionRepository, accountRepository, exchangeRateRepository);

        //Register health checks
        environment.healthChecks().register("Database heals check", new DatabaseHealthCheck());

        //Register resources
        environment.jersey().register(new UserResource(userService, accountService, transferService));
        environment.jersey().register(new TransferResource(transferService));
        environment.jersey().register(new AccountResource(accountService));
        environment.jersey().register(new GenericApplicationExceptionMapper());
        environment.jersey().register(new NotFoundExceptionMapper());
    }
}
