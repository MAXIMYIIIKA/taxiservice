package by.nichipor.taxiservice.service.dispatcherpanel.controller;

import by.nichipor.taxiservice.service.ordermananger.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Max Nichipor on 26.08.2016.
 */

@Controller
@RequestMapping("/dispatcher")
public class DispatcherPanelController {
    private static final String DISPATCHER_PAGE = "dispatcher";

    @Autowired
    OrderManager orderManager;

    @RequestMapping(method = RequestMethod.GET)
    public void dispatherPage(Model ui){
        showAllOrders(ui);
    }

    @RequestMapping(params = {"showAllOrders"}, method = RequestMethod.GET)
    public String showAllOrders(Model ui){
        ui.addAttribute("orders", orderManager.findAllOrders());
        return DISPATCHER_PAGE;
    }

    @RequestMapping(params = {"deleteOrder"}, method = RequestMethod.GET)
    public void deleteOrder(@RequestParam("deleteOrder") int orderId, Model ui){
        orderManager.deleteOrder(orderId);
        showAllOrders(ui);
    }
}
