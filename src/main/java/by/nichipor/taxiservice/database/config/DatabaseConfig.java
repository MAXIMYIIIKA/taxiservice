package by.nichipor.taxiservice.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ResourceBundle;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Configuration
public class DatabaseConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(resourceBundle.getString("db.driver"));
        driverManagerDataSource.setUrl(resourceBundle.getString("db.url"));
        driverManagerDataSource.setUsername(resourceBundle.getString("db.username"));
        driverManagerDataSource.setPassword(resourceBundle.getString("db.password"));
        return driverManagerDataSource;
    }
}
