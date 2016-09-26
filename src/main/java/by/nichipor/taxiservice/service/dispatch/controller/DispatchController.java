package by.nichipor.taxiservice.service.dispatch.controller;

import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.OrderStatus;
import by.nichipor.taxiservice.service.notifier.DispatcherNotifier;
import by.nichipor.taxiservice.service.notifier.Notifier;
import by.nichipor.taxiservice.service.ordermananger.OrderManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Controller
@RequestMapping("/dispatch")
public class DispatchController {
    private static Logger logger = Logger.getLogger(DispatchController.class);

    private static final String DISPATCH_PAGE = "dispatch";

    @Autowired
    private volatile OrderManager orderManager;

    @Autowired
    private Notifier notifier;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return DISPATCH_PAGE;
    }

    @RequestMapping(value = "ajaxShowOrders")
    @ResponseBody
    public Map<String, List<Order>> showOrders() {
        Map<String, List<Order>> records = new HashMap<>();
        records.put("orders", orderManager.findAllOrders());
        return records;
    }

    @RequestMapping(value = "ajaxCheck")
    @ResponseBody
    public DeferredResult<String> check() throws InterruptedException{
        DispatcherNotifier dispatcher = new DispatcherNotifier();
        notifier.registerDispatcher(dispatcher);
        DeferredResult<String> result = new DeferredResult<>();
        dispatcher.getUpdate(result);
        logger.debug("getUpdate(" + result.hashCode() + ") called on " + dispatcher.hashCode()
                        + "; Return value = " + result.getResult());
        return result;
    }

    @RequestMapping(value = "ajaxChangeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public boolean changeOrderStatus(@RequestParam("orderId") int orderId,
                                  @RequestParam("status") String status){
        logger.debug(this.hashCode() + ": CONTROLLER params = {" + orderId + ", " + status + "}");
        Order order = orderManager.getOrderById(orderId);
        logger.debug(this.hashCode() + ": CONTROLLER order : " + order.toString());
        orderManager.changeOrderStatus(order, OrderStatus.valueOf(status));
        return true;
    }
}
