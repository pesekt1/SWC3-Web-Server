package swc3.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

//@Configuration
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "db1EntityMgrFactory",
//        transactionManagerRef = "db1TransactionMgr",
//        basePackages = {"swc3.server.repository"}
//        )
public class DataSourceBean {

//    @Autowired
//    private Environment env;
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("datasource1")
//    public DataSourceProperties db1DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("datasource1.configuration")
//    public DataSource db1DataSource() {
//        return db1DataSourceProperties().initializeDataSourceBuilder()
//                .type(HikariDataSource.class).build();
//    }
//
//    @Bean(name = "datasource1")
//    @Primary
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.url(env.getProperty("DATABASE_URL"));
//        dataSourceBuilder.username(env.getProperty("DATABASE_USER_NAME"));
//        dataSourceBuilder.password(env.getProperty("MYSQL_PASSWORD"));
//        return dataSourceBuilder.build();
//    }
//
//    @Bean
//    @Primary
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
//        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
//    }
//
//    @Bean(name = "db1EntityMgrFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean db1EntityMgrFactory(
//            final EntityManagerFactoryBuilder builder,
//            @Qualifier("datasource1") final DataSource dataSource) {
////        // dynamically setting up the hibernate properties for each of the datasource.
////        final Map<String, String> properties = new HashMap<>();
////        properties.put("hibernate.hbm2ddl.auto", "create-drop");
////        // in springboot2 the dialect can be automatically detected.
////        // we are setting up here just to avoid any incident.
////        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        return builder
//                .dataSource(dataSource)
//                //.properties(properties)
//                .packages("swc3.server.model")
//                .persistenceUnit("db1")
//                .build();
//    }
//
//    @Bean(name = "db1TransactionMgr")
//    @Primary
//    public PlatformTransactionManager db1TransactionMgr(
//            @Qualifier("db1EntityMgrFactory") final EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
}