package swc3.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDb2",
        transactionManagerRef = "transactionManagerDb2",
        basePackages = {"swc3.server.Datasources.Db2.repo"})
public class Db2Config {

    @Autowired
    private Environment env;

    @Bean(name = "dataSourceDb2")
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryDb2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDb2") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("swc3.server.Datasources.Db2.models")
                .persistenceUnit("db2")
                .build();
    }

    @Bean(name = "transactionManagerDb2")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDb2") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "jdbcTemplateDb2")
    public JdbcTemplate jdbcTemplateDb2(@Qualifier("dataSourceDb2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}