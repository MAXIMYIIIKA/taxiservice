package by.taxiservice.test.dao;

import by.nichipor.taxiservice.database.dao.OrderDAO;
import by.nichipor.taxiservice.entity.Location;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.entity.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



@ContextConfiguration("classpath:appServlet-servlet-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderDAOImplTest {
    private Order order;
    private User testUser;
    private User user;
    private int numberOfOrders;

    @Autowired
    private OrderDAO orderDAO;

    @Before
    public void setUp(){
        user = new User("user", "user");
        testUser = new User("test", "test");
        order = new Order("test", new Location(53.253425, 32.53463), "+35888888");
        numberOfOrders = 3;
    }

    @Test
    public void findAllOrdersTest() throws Exception {
        int expected = numberOfOrders;
        int actual = orderDAO.findAllOrders().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void addOrderTest() throws Exception {
        orderDAO.addOrder(order);
        int expected = ++numberOfOrders;
        int actual = orderDAO.findAllOrders().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void deleteOrderTest() throws Exception {
        order = orderDAO.findAllUsersOrders(testUser).get(0);
        orderDAO.deleteOrder(order);
        int expected = --numberOfOrders;
        int actual = orderDAO.findAllOrders().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void findAllUsersOrdersTest() throws Exception {
        int expected = 2;
        int actual = orderDAO.findAllUsersOrders(user).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void findOrderByIdTest() throws Exception {
        int id = 1;
        Order expected = orderDAO.findAllUsersOrders(user).get(0);
        Order actual = orderDAO.findOrderById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOrdersByPhoneTest() throws Exception {
        int expected = 1;
        int actual = orderDAO.findOrdersByPhone("test").size();
        Assert.assertEquals(expected, actual, 0);
    }


    @Test
    public void findAllAcceptedUserOrdersTest() throws Exception {
        int expected = 1;
        int actual = orderDAO.findAllAcceptedUserOrders(user).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void changeOrderStatusTest() throws Exception {
        Assert.assertTrue(orderDAO.changeOrderStatus(orderDAO.findAllUsersOrders(user).get(0),
                OrderStatus.ACCEPTED));
    }

}