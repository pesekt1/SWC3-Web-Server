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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryDb4",
        basePackages = {"swc3.server.Db4.repo"})
public class Db4Config {

    @Autowired
    private Environment env;

    @Bean(name = "dataSourceDb4")
    @ConfigurationProperties(prefix = "db4.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryDb4")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDb4") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("db4.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("db4.jpa.properties.hibernate.dialect"));

        return builder
                .dataSource(dataSource)
                .packages("swc3.server.Db4.models")
                .persistenceUnit("db4")
                .properties(properties)
                .build();
    }

    @Bean(name = "transactionManagerDb4")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDb4") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}