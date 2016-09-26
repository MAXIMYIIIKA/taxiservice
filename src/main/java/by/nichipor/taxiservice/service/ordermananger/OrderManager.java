package by.nichipor.taxiservice.service.ordermananger;

import by.nichipor.taxiservice.database.dao.OrderDAO;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.service.notifier.Notifier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Nichipor on 28.08.2016.
 */

/**
 * This class is a "client-side" layer for connection to the DAO layer.
 *
 * <p>This class provide a secure access to the {@linkplain OrderDAO DAO layer}.</p>
 *
 * @author Max Nichipor
 */

@Service
public class OrderManager {

    private static Logger logger = Logger.getLogger(OrderManager.class);


    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private Notifier notifier;

    public List<Order> findAllOrders() {
        try {
            return orderDAO.findAllOrders();
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return new ArrayList<>();
    }

    public boolean addOrder(Order order) {
        try {
            if (orderDAO.addOrder(order)){
                notifier.addOrderNotification();
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean isExist(int orderId) {
        try {
            return orderId > 0 && orderDAO.findOrderById(orderId) != null;
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        if (isExist(orderId)) {
            try {
                order = orderDAO.findOrderById(orderId);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return order;
    }

    public boolean changeOrderStatus(Order order, OrderStatus status) {
        try {
            if (isExist(order.getOrderId()) && orderDAO.changeOrderStatus(order, status)){
                notifier.addStatusNotification();
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean deleteOrder(int orderId) {
        try {
            if (isExist(orderId)) {
                Order order = orderDAO.findOrderById(orderId);
                orderDAO.deleteOrder(order);
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }
}
