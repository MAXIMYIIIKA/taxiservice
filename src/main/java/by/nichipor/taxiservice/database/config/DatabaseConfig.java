package by.nichipor.taxiservice.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

/**
 * This class is a configuration class for the database connection.
 * @author Max Nichipor
 */

@Configuration
public class DatabaseConfig {

    /**
     * Creates a bean with the database connection properties.
     *
     * <p>
     *     This method uses a DriverManagerDataSource object to configure
     *     a database connection properties.
     * </p>
     *
     * <p>
     *     DriverManagerDataSource is used as a data source object because
     *     it returns a new connection every time a connection is requested.
     * </p>
     *
     * @return Data source i.e. a DriverManagerDataSource object.
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(resourceBundle.getString("db.driver"));
        driverManagerDataSource.setUrl(resourceBundle.getString("db.url"));
        driverManagerDataSource.setUsername(resourceBundle.getString("db.username"));
        driverManagerDataSource.setPassword(resourceBundle.getString("db.password"));
        return driverManagerDataSource;
    }
}
