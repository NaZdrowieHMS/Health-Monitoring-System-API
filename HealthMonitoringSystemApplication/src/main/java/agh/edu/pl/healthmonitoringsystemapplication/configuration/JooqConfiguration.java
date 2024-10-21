package agh.edu.pl.healthmonitoringsystemapplication.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class JooqConfiguration {

    private final Environment env;

    public JooqConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(env.getProperty("DATABASE_URL"));
        dataSource.setUsername(env.getProperty("DATABASE_USERNAME"));
        dataSource.setPassword(env.getProperty("DATABASE_PASSWORD"));
        return dataSource;
    }

    @Bean
    public DSLContext dslContext() {
        return DSL.using(dataSource(), SQLDialect.POSTGRES);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

