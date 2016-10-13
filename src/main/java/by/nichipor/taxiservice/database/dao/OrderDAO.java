package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.entity.User;

import java.util.List;

/**
 * Created by Max Nichipor on 24.08.2016.
 */

/**
 * Describes the order data access object layer.
 * @author Max Nichipor
 */
public interface OrderDAO {
    /**
     * Returns a list of all {@link Order orders} from database.
     * @return a list of {@link Order orders}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllOrders() throws InterruptedException;

    /**
     * Finds the specified {@link Order order} in the database by order ID.
     * @param orderId order id.
     * @return an {@link Order order}
     * @throws InterruptedException if interrupted while getting connection.
     */
    Order findOrderById(int orderId) throws InterruptedException;

    /**
     * Finds {@link Order orders} in database by user's phone number.
     * @param phone user's phone number.
     * @return a list of {@link Order orders}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findOrdersByPhone(String phone) throws InterruptedException;

    /**
     * Returns a list of all user's {@link Order orders} from database.
     * @param user the {@link User user} whose {@link Order orders} we are looking for.
     * @return a list of {@link Order orders}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllUsersOrders(User user) throws InterruptedException;

    /**
     * Returns a list of all accepted user's {@link Order orders} from database
     * @param user the {@link User user} whose {@link Order orders} we are looking for.
     * @return a list of {@link Order orders}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Order> findAllAcceptedUserOrders(User user) throws InterruptedException;

    /**
     * Counts all user's {@link Order orders}.
     * @param user the {@link User user} whose {@link Order orders} we are looking for.
     * @return number of {@link Order orders}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    int findNumberOfUserOrders(User user) throws InterruptedException;

    /**
     * Adds a new {@link Order order} to the database.
     * @param order an {@link Order order} to add.
     * @return true if the order added successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean addOrder(Order order) throws InterruptedException;

    /**
     * Deletes specified {@link Order order} from the database.
     * @param order an {@link Order order} to be deleted.
     * @return true if the order deleted successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteOrder(Order order) throws InterruptedException;

    /**
     * Changes the {@link OrderStatus status} of the specified {@link Order order}.
     * @param order the {@link Order order} whose {@link OrderStatus status} will be changed.
     * @param status a new {@link OrderStatus status} of the {@link Order order}.
     * @return true if the status of the order changed successfully;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean changeOrderStatus(Order order, OrderStatus status) throws InterruptedException;
}
