package by.nichipor.taxiservice.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max Nichipor on 12.09.2016.
 */

/**
 * This is a database connection pool.
 *
 * <p>
 *     It's a simple implementation of {@linkplain DBConnPool DBConnPool} interface.
 * </p>
 * @author Max Nichipor
 */

@Component
public class DBConnPoolImpl implements DBConnPool{
    private BlockingQueue<Connection> connections;
    private static int poolSize = 20;
    private static int timeout = 2;

    /**
     * Creates a connection pool with the size of the specified number of connections.
     *
     * @param dataSource A DataSource object.
     * @throws SQLException  if a database access error occurs or
     * this method is called on a closed connection.
     */
    @Autowired
    public DBConnPoolImpl(DataSource dataSource) throws SQLException{
        connections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++){
            connections.add(dataSource.getConnection());
        }
    }


    /**
     * Takes a connection from the pool.
     *
     * <p>
     *     Waiting up to the specified wait time
     *     if necessary for an element to become available.
     * </p>
     *
     * @return a Connection object.
     * @throws InterruptedException if interrupted while waiting.
     */
    @Override
    public Connection getConnection() throws InterruptedException{
        return connections.poll(timeout, TimeUnit.SECONDS);

    }

    /**
     * Puts back the connection to the connection pool.
     *
     * @param connection to put to the connection pool (not null).
     * @throws InterruptedException if interrupted while waiting.
     */
    @Override
    public void putConnection(Connection connection) throws InterruptedException{
        if (connection != null){
            connections.put(connection);
        }
    }

}
