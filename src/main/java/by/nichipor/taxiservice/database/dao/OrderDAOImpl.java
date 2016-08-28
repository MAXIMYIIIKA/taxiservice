package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Nichipor on 25.08.2016.
 */

@Component
public class OrderDAOImpl implements OrderDAO {

    private static Logger logger = Logger.getLogger(OrderDAOImpl.class);

    private final static String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY status DESC";
    private final static String SQL_INSERT_ORDER = "INSERT INTO orders (username, currLat, currLng, targLat, targLng, date, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_ORDER = "UPDATE orders SET status = ? WHERE orderId = ?";
    private final static String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE orderId = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        Order tempOrder;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS)) {
            while (resultSet.next()){
                tempOrder = new Order(resultSet.getInt("orderId"),
                                        resultSet.getString("username"),
                                        new Location(resultSet.getDouble("currLat"),
                                                    resultSet.getDouble("currLng")),
                                        new Location(resultSet.getDouble("targLat"),
                                                    resultSet.getDouble("targLng")),
                                        new DateTime(resultSet.getDate("date"),
                                                    resultSet.getTime("date")),
                                        OrderStatus.valueOf(resultSet.getString("status")),
                                        resultSet.getString("phone"));
                orders.add(tempOrder);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return orders;
    }

    @Override
    public Order findOrderById(int orderId) {
        Order order = null;
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            order = new Order(resultSet.getInt("orderId"),
                                resultSet.getString("username"),
                                new Location(resultSet.getDouble("currLat"),
                                        resultSet.getDouble("currLng")),
                                new Location(resultSet.getDouble("targLat"),
                                        resultSet.getDouble("targLng")),
                                new DateTime(resultSet.getDate("date"),
                                        resultSet.getTime("date")),
                                OrderStatus.valueOf(resultSet.getString("status")),
                                resultSet.getString("phone"));
        } catch (SQLException e){
            logger.error(e);
        }
        return order;
    }

    @Override
    public List<Order> findOrderByPhone(String telephone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findOrdersTillDate(String dateTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findOrdersByPeriod(String fromDateTime, String toDateTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findAllUsersOrders(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addOrder(Order order) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER)) {
            preparedStatement.setString(1, order.getUsername());
            preparedStatement.setDouble(2, order.getCurrentLocation().getLat());
            preparedStatement.setDouble(3, order.getCurrentLocation().getLng());
            preparedStatement.setDouble(4, order.getTargetLocation().getLat());
            preparedStatement.setDouble(5, order.getTargetLocation().getLng());
            preparedStatement.setString(6, order.getDateTime().getDate() + " " + order.getDateTime().getTime());
            preparedStatement.setString(7, order.getPhone());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean changeOrderStatus(Order order, OrderStatus status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, order.getOrderId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

}
