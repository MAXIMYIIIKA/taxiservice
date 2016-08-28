package by.nichipor.taxiservice.service.ordermananger;

import by.nichipor.taxiservice.database.dao.OrderDAO;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.service.notifier.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Max Nichipor on 28.08.2016.
 */

@Service
public class OrderManager {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private Notifier notifier;

    public List<Order> findAllOrders(){
        return orderDAO.findAllOrders();
    }

    public boolean addOrder(Order order){
        if (orderDAO.addOrder(order)){
            notifier.addNewOrderNotification();
            return true;
        }
        return false;
    }

    public boolean isExist(int orderId) {
        return orderId > 0 && orderDAO.findOrderById(orderId) != null;
    }

    public Order getOrderById(int orderId){
        Order order = null;
        if (isExist(orderId)) {
            order = orderDAO.findOrderById(orderId);
        }
        return order;
    }

    public boolean changeOrderStatus(Order order, OrderStatus status){
        if (isExist(order.getOrderId()) && orderDAO.changeOrderStatus(order, status)){
            notifier.addNewStatusNotification();
            return true;
        }
        return false;
    }
}
