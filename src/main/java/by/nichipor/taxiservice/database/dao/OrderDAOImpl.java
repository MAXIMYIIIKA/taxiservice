package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.database.config.DBConnPool;
import by.nichipor.taxiservice.entity.*;
import by.nichipor.taxiservice.entity.type.DateTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Nichipor on 25.08.2016.
 */


/**
 * This is the implementation of the {@linkplain OrderDAO order data access object}.
 * @author Max Nichipor
 */
@Component
public class OrderDAOImpl implements OrderDAO {

    private static Logger logger = Logger.getLogger(OrderDAOImpl.class);

    private static final String SQL_SELECT_ALL_ORDERS = "SELECT table_1.*, locations.lat as targLat, locations.lng as targLng\n" +
                                                        "FROM (SELECT orders.*, locations.lat as currLat, locations.lng as currLng\n" +
                                                        "FROM orders\n" +
                                                        "  INNER JOIN locations\n" +
                                                        "    ON currLocationId = locations.locationId) as table_1\n" +
                                                        "INNER JOIN locations\n" +
                                                        "    ON table_1.targLocationId = locations.locationId ORDER BY status DESC";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (username, currLocationId, targLocationId, date, phone) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_LOCATION = "INSERT INTO locations (lat, lng) VALUES (?, ?)";
    private static final String SQL_SELECT_LOCAION_BY_LAT_LNG = "SELECT * FROM locations WHERE lat = ? AND lng = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = ? WHERE orderId = ?";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT table_1.*, locations.lat as targLat, locations.lng as targLng\n" +
                                                        "FROM (SELECT orders.*, locations.lat as currLat, locations.lng as currLng\n" +
                                                        "FROM orders\n" +
                                                        "  INNER JOIN locations\n" +
                                                        "    ON currLocationId = locations.locationId) as table_1\n" +
                                                        "INNER JOIN locations\n" +
                                                        "    ON table_1.targLocationId = locations.locationId\n"+
                                                        "WHERE orderId = ?";
    private static final String SQL_SELECT_ORDERS_BY_PHONE = "SELECT table_1.*, locations.lat as targLat, locations.lng as targLng\n" +
                                                            "FROM (SELECT orders.*, locations.lat as currLat, locations.lng as currLng\n" +
                                                            "FROM orders\n" +
                                                            "  INNER JOIN locations\n" +
                                                            "    ON currLocationId = locations.locationId) as table_1\n" +
                                                            "INNER JOIN locations\n" +
                                                            "    ON table_1.targLocationId = locations.locationId\n"+
                                                            "WHERE phone = ?";
    private static final String SQL_SELECT_ORDERS_BY_USERNAME = "SELECT table_1.*, locations.lat as targLat, locations.lng as targLng\n" +
                                                                "FROM (SELECT orders.*, locations.lat as currLat, locations.lng as currLng\n" +
                                                                "FROM orders\n" +
                                                                "  INNER JOIN locations\n" +
                                                                "    ON currLocationId = locations.locationId) as table_1\n" +
                                                                "INNER JOIN locations\n" +
                                                                "    ON table_1.targLocationId = locations.locationId\n" +
                                                                "WHERE username = ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE orderId = ?";
    private static final String SQL_DELETE_LOCATION = "DELETE FROM locations WHERE locationId = ?";
    private static final String SQL_SELECT_USER_ORDERS_BY_STATUS = "SELECT table_1.*, locations.lat as targLat, locations.lng as targLng\n" +
                                                                    "FROM (SELECT orders.*, locations.lat as currLat, locations.lng as currLng\n" +
                                                                    "FROM orders\n" +
                                                                    "  INNER JOIN locations\n" +
                                                                    "    ON currLocationId = locations.locationId) as table_1\n" +
                                                                    "INNER JOIN locations\n" +
                                                                    "    ON table_1.targLocationId = locations.locationId\n" +
                                                                    "WHERE username = ? AND status = ?";
    private static final String SQL_COUNT_USER_ORDERS = "SELECT COUNT(*) FROM orders WHERE username = ?";
    private static final String ORDER_ID_FIELD = "orderId";
    private static final String USERNAME_FIELD = "username";
    private static final String LOCATION_ID_FIELD = "locationId";
    private static final String LAT_FIELD = "lat";
    private static final String LNG_FIELD = "lng";
    private static final String CURRLAT_FIELD = "currLat";
    private static final String CURRLNG_FIELD = "currLng";
    private static final String TARGLAT_FIELD = "targLat";
    private static final String TARGLNG_FIELD = "targLng";
    private static final String DATE_FIELD = "date";
    private static final String STATUS_FIELD = "status";
    private static final String PHONE_FIELD = "phone";


    @Autowired
    private DBConnPool dbConnPool;

    @Override
    public List<Order> findAllOrders() throws InterruptedException{
        List<Order> orders = new ArrayList<>();
        Connection connection = dbConnPool.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS)) {
            while (resultSet.next()){
                Order tempOrder = new Order(resultSet.getInt(ORDER_ID_FIELD),
                        resultSet.getString(USERNAME_FIELD),
                        new Location(resultSet.getDouble(CURRLAT_FIELD),
                                resultSet.getDouble(CURRLNG_FIELD)),
                        new Location(resultSet.getDouble(TARGLAT_FIELD),
                                resultSet.getDouble(TARGLNG_FIELD)),
                        new DateTime(resultSet.getDate(DATE_FIELD),
                                resultSet.getTime(DATE_FIELD)),
                        OrderStatus.valueOf(resultSet.getString(STATUS_FIELD)),
                        resultSet.getString(PHONE_FIELD));
                orders.add(tempOrder);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return orders;
    }


    @Override
    public Order findOrderById(int orderId) throws InterruptedException{
        Order order = null;
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            order = new Order(resultSet.getInt(ORDER_ID_FIELD),
                                resultSet.getString(USERNAME_FIELD),
                                new Location(resultSet.getDouble(CURRLAT_FIELD),
                                        resultSet.getDouble(CURRLNG_FIELD)),
                                new Location(resultSet.getDouble(TARGLAT_FIELD),
                                        resultSet.getDouble(TARGLNG_FIELD)),
                                new DateTime(resultSet.getDate(DATE_FIELD),
                                        resultSet.getTime(DATE_FIELD)),
                                OrderStatus.valueOf(resultSet.getString(STATUS_FIELD)),
                                resultSet.getString(PHONE_FIELD));
        } catch (SQLException e){
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return order;
    }

    /**
     * This method is used to find the order in database by SQL query and one parameter.
     *
     * <p>
     *     This method calls
     *     {@linkplain #findOrdersByString(String, String, String) findOrderByString} method
     *     with the second parameter which equals null.
     * </p>
     *
     * @param sql SQL query.
     * @param param1 SQL query parameter.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    private List<Order> findOrdersByString(String sql, String param1) throws InterruptedException{
        return findOrdersByString(sql, param1, null);
    }

    /**
     * This method is used to find  the order in database by SQL query and two parameters.
     *
     * <p>
     *     This method will be called only when two parameters will not null.
     * </p>
     *
     * @param sql SQL query.
     * @param param1 the first SQL query parameter.
     * @param param2 the second SQL query parameter.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    private List<Order> findOrdersByString(String sql, String param1, String param2) throws InterruptedException{
        List<Order> orders = new ArrayList<>();
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, param1);
            if (param2 != null) {
                preparedStatement.setString(2, param2);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order tempOrder = new Order(resultSet.getInt(ORDER_ID_FIELD),
                        resultSet.getString(USERNAME_FIELD),
                        new Location(resultSet.getDouble(CURRLAT_FIELD),
                                resultSet.getDouble(CURRLNG_FIELD)),
                        new Location(resultSet.getDouble(TARGLAT_FIELD),
                                resultSet.getDouble(TARGLNG_FIELD)),
                        new DateTime(resultSet.getDate(DATE_FIELD),
                                resultSet.getTime(DATE_FIELD)),
                        OrderStatus.valueOf(resultSet.getString(STATUS_FIELD)),
                        resultSet.getString(PHONE_FIELD));
                orders.add(tempOrder);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByPhone(String phone) throws InterruptedException {
        return findOrdersByString(SQL_SELECT_ORDERS_BY_PHONE, phone);
    }

    @Override
    public List<Order> findAllUsersOrders(User user) throws InterruptedException {
        return findOrdersByString(SQL_SELECT_ORDERS_BY_USERNAME, user.getUsername());
    }

    @Override
    public List<Order> findAllAcceptedUserOrders(User user) throws InterruptedException{
        return findOrdersByString(SQL_SELECT_USER_ORDERS_BY_STATUS, user.getUsername(), OrderStatus.ACCEPTED.name());
    }

    @Override
    public int findNumberOfUserOrders(User user) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_USER_ORDERS)){
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return 0;
    }

    @Override
    public boolean addOrder(Order order) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        int currLocationId;
        int targLocationId;
        if (addLocation(connection, order.getCurrentLocation()) && addLocation(connection, order.getTargetLocation())) {
            currLocationId = findLocationId(connection, order.getCurrentLocation());
            targLocationId = findLocationId(connection, order.getTargetLocation());
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER)) {
                preparedStatement.setString(1, order.getUsername());
                preparedStatement.setInt(2, currLocationId);
                preparedStatement.setInt(3, targLocationId);
                preparedStatement.setString(4, order.getDateTime().toString());
                preparedStatement.setString(5, order.getPhone());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error(e);
            } finally {
                dbConnPool.putConnection(connection);
            }
        }
        dbConnPool.putConnection(connection);
        return false;
    }

    /**
     * This method is used locally for adding a {@link Location} to the database.
     * @param connection current connection which is taken from connection pool by the method invokes this.
     * @param location the {@link Location} to add.
     * @return true if adding has been successful; false if it is not.
     */
    private boolean addLocation(Connection connection, Location location) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_LOCATION)){
            preparedStatement.setDouble(1, location.getLat());
            preparedStatement.setDouble(2, location.getLng());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    /**
     * This method is used locally to find the specified {@link Location} id in the database.
     * @param connection current connection which is taken from connection pool by the method invokes this.
     * @param location the {@link Location} which id we are looking for.
     * @return id if found; -1 if there is no such a location.
     */
    private int findLocationId(Connection connection,Location location){
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LOCAION_BY_LAT_LNG)){
            preparedStatement.setDouble(1, location.getLat());
            preparedStatement.setDouble(2, location.getLng());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(LOCATION_ID_FIELD);
        } catch (SQLException e) {
            logger.error(e);
        }
        return -1;
    }

    /**
     * This method is used locally for deleting specified location from database.
     * @param connection current connection is taken from connection pool by the method invokes this.
     * @param locationId id of the location to delete.
     * @return true if deleting has been successful; false if it not.
     */
    private boolean deleteLocation(Connection connection, int locationId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LOCATION)){
            preparedStatement.setInt(1, locationId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Order order) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        int currLocationId = findLocationId(connection, order.getCurrentLocation());
        int targLocationId = findLocationId(connection, order.getTargetLocation());
        if (deleteLocation(connection, currLocationId) && deleteLocation(connection, targLocationId)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER)) {
                preparedStatement.setInt(1, order.getOrderId());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error(e);
            } finally {
                dbConnPool.putConnection(connection);
            }
        }
        dbConnPool.putConnection(connection);
        return false;
    }

    @Override
    public boolean changeOrderStatus(Order order, OrderStatus status) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, order.getOrderId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

}
