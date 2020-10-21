package swc3.server;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;



//@Component
//@Profile("dev")
@Configuration
public class DatabaseConfig {



//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${spring.datasource.password}")
//    private String dbPassword;
//
//    @Value("${spring.datasource.username}")
//    private String dbUser;
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(dbUrl);
//        config.setPassword(dbPassword);
//        config.setUsername(dbUser);
//        return new HikariDataSource(config);
//    }
}
