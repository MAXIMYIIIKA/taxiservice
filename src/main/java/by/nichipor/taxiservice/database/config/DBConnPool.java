package by.nichipor.taxiservice.database.config;

import java.sql.Connection;

/**
 * Created by Max Nichipor on 13.09.2016.
 */


/**
 *  The DBConnPool interface describes a simple database connection pool.
 *  @author Max Nichipor
 */
public interface DBConnPool {
    Connection getConnection() throws InterruptedException;
    void putConnection(Connection connection) throws InterruptedException;
}
