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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDb3",
        transactionManagerRef = "transactionManagerDb3",
        basePackages = {"swc3.server.Datasources.Db3.repo"})
public class Db3Config {

    @Autowired
    private Environment env;

    @Bean(name = "dataSourceDb3")
    @ConfigurationProperties(prefix = "db3.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryDb3")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDb3") DataSource dataSource) {

//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto",
//                env.getProperty("db3.jpa.hibernate.ddl-auto"));
//        properties.put("hibernate.dialect",
//                env.getProperty("db3.jpa.properties.hibernate.dialect"));

        return builder
                .dataSource(dataSource)
                .packages("swc3.server.Datasources.Db3.models")
                .persistenceUnit("db3")
                //.properties(properties)
                .build();
    }

    @Bean(name = "transactionManagerDb3")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDb3") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}