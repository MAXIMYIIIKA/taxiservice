package by.nichipor.taxiservice.service.dispatcherpanel.controller;

import by.nichipor.taxiservice.database.dao.OrderDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max Nichipor on 26.08.2016.
 */

@Controller
@RequestMapping("/dispatcher")
public class DispatcherPanelController {

    @Autowired
    OrderDAOImpl orderDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String dispathPage(){
        return "dispatcher";
    }

    @RequestMapping(params = {"showAllOrders"}, method = RequestMethod.GET)
    public String showAllOrders(Model ui){
        ui.addAttribute("orders", orderDAO.findAllOrders());
        return "dispatcher";
    }
}
