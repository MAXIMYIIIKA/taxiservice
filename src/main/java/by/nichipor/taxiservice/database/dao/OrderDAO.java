package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.entity.User;

import java.util.List;

/**
 * Created by Max Nichipor on 24.08.2016.
 */
public interface OrderDAO {
    List<Order> findAllOrders();
    Order findOrderById(int orderId);
    List<Order> findOrderByPhone(String telephone);
    List<Order> findOrdersTillDate(String dateTime);
    List<Order> findOrdersByPeriod(String fromDateTime, String toDateTime);
    List<Order> findAllUsersOrders(User user);
    boolean addOrder(Order order);
    boolean deleteOrder(Order order);
    boolean changeOrderStatus(Order order, OrderStatus status);
}
