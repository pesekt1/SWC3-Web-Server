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
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDb6",
        basePackages = {"swc3.server.Datasources.Db6.repo"})
//@PropertySource("persistence-sqlite.properties")
public class Db6Config {

    @Autowired
    private Environment env;

    @Bean(name = "dataSourceDb6")
    @ConfigurationProperties(prefix = "db6.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryDb6")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDb6") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("db6.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("db6.jpa.properties.hibernate.dialect"));

        return builder
                .dataSource(dataSource)
                .packages("swc3.server.Datasources.Db6.models")
                .persistenceUnit("db6")
                .properties(properties)
                .build();
    }

    @Bean(name = "transactionManagerDb6")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDb6") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

//    final Properties additionalProperties() {
//        final Properties hibernateProperties = new Properties();
//        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
//            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        }
//        if (env.getProperty("hibernate.dialect") != null) {
//            hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        }
//        if (env.getProperty("hibernate.show_sql") != null) {
//            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        }
//        return hibernateProperties;
//    }

}
