package swc3.server.profiles;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("swc3.server.profiles")
@PropertySource(value = "classpath:application.properties")
public class SpringProfilesConfig {

}