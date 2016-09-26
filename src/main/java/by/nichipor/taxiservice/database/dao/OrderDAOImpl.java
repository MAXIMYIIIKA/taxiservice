package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.database.config.DBConnPool;
import by.nichipor.taxiservice.entity.*;
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

    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY status DESC";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (username, currLat, currLng, targLat, targLng, date, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = ? WHERE orderId = ?";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE orderId = ?";
    private static final String SQL_SELECT_ORDERS_BY_PHONE = "SELECT * FROM orders WHERE phone = ?";
    private static final String SQL_SELECT_ORDER_TILL_DATE = "SELECT * FROM orders WHERE date > ?";
    private static final String SQL_SELECT_ORDERS_BY_USERNAME = "SELECT * FROM orders WHERE username = ?";
    private static final String SQL_SELECT_ORDER_FROM_PERIOD = "SELECT * FROM orders WHERE date > ? AND date < ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE orderId = ?";
    private static final String SQL_DELETE_DENIED_ORDERS = "DELETE FROM orders WHERE status = DENIED";
    private static final String SQL_SELECT_USER_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE username = ? AND status = ?";
    private static final String SQL_COUNT_USER_ORDERS = "SELECT COUNT(*) FROM orders WHERE username = ?";
    private static final String ORDER_ID_FIELD = "orderId";
    private static final String USERNAME_FIELD = "username";
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
    public List<Order> findOrdersTillDate(DateTime dateTime) throws InterruptedException{
        return findOrdersByString(SQL_SELECT_ORDER_TILL_DATE, dateTime.toString());
    }

    @Override
    public List<Order> findOrdersByPeriod(DateTime fromDateTime, DateTime toDateTime) throws InterruptedException{
        return findOrdersByString(SQL_SELECT_ORDER_FROM_PERIOD, fromDateTime.toString(), toDateTime.toString());
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER)) {
            preparedStatement.setString(1, order.getUsername());
            preparedStatement.setDouble(2, order.getCurrentLocation().getLat());
            preparedStatement.setDouble(3, order.getCurrentLocation().getLng());
            preparedStatement.setDouble(4, order.getTargetLocation().getLat());
            preparedStatement.setDouble(5, order.getTargetLocation().getLng());
            preparedStatement.setString(6, order.getDateTime().getDate() + " " + order.getDateTime().getTime());
            preparedStatement.setString(7, order.getPhone());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Order order) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER)){
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteAllDeniedOrders() throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(SQL_DELETE_DENIED_ORDERS);
        } catch (SQLException e){
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
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
