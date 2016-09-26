package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.DateTime;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.entity.User;

import java.util.List;

/**
 * Created by Max Nichipor on 24.08.2016.
 */

/**
 * This interface describes the order data access object layer.
 * @author Max Nichipor
 */
public interface OrderDAO {
    /**
     * This method is used to list all orders from database.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllOrders() throws InterruptedException;

    /**
     * This method is used to find the order in database by order ID.
     * @param orderId order ID
     * @return order
     * @throws InterruptedException if interrupted while getting connection.
     */
    Order findOrderById(int orderId) throws InterruptedException;

    /**
     * This method is used to find orders in database by user's phone number.
     * @param phone user's phone number.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findOrdersByPhone(String phone) throws InterruptedException;

    /**
     * This method is used to find orders in database from that time till specified date.
     * @param dateTime the end of the period.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findOrdersTillDate(DateTime dateTime) throws InterruptedException;

    /**
     * This method is used to find orders in database for the specified period.
     * @param fromDateTime the start of the period.
     * @param toDateTime the end of the period.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findOrdersByPeriod(DateTime fromDateTime, DateTime toDateTime) throws InterruptedException;

    /**
     * This method is used to list all user's orders from database.
     *
     * @param user The user whose orders we are looking for.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllUsersOrders(User user) throws InterruptedException;

    /**
     * This method is used for list all accepted user's orders from database.
     *
     * @param user The user whose orders we are looking for.
     * @return a list of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllAcceptedUserOrders(User user) throws InterruptedException;

    /**
     * This method is used for counting all user's orders.
     * @param user The user whose orders we are looking for.
     * @return number of orders.
     * @throws InterruptedException if interrupted while getting connection.
     */
    int findNumberOfUserOrders(User user) throws InterruptedException;

    /**
     * This method is used to add a new order to the database.
     * @param order A new order to add.
     * @return true if the order added successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean addOrder(Order order) throws InterruptedException;

    /**
     * This method is used to delete specified order from the database.
     * @param order An order to be deleted.
     * @return true if the order deleted successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteOrder(Order order) throws InterruptedException;

    /**
     * This method is used for deleting all denied orders from the database.
     * @return true if orders deleted successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteAllDeniedOrders() throws InterruptedException;

    /**
     * This method is used to change the status of the specified order.
     * @param order The order whose status will be changed.
     * @param status New status of the order.
     * @return true if the status of the order changed successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean changeOrderStatus(Order order, OrderStatus status) throws InterruptedException;
}
