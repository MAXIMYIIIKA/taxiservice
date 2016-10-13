package by.nichipor.taxiservice.database.config;

import java.sql.Connection;

/**
 * Created by Max Nichipor on 13.09.2016.
 */


/**
 *  Describes a simple database connection pool.
 *  @author Max Nichipor
 */
public interface DBConnPool {

    /**
     * Takes a {@link Connection connection} from the pool.
     * @return a {@link Connection connection}
     * @throws InterruptedException if interrupted while waiting.
     */
    Connection getConnection() throws InterruptedException;

    /**
     * Puts back the {@link Connection connection} to the connection pool.
     * @param connection the {@link Connection connection} to put.
     * @throws InterruptedException if interrupted while waiting.
     */
    void putConnection(Connection connection) throws InterruptedException;
}
