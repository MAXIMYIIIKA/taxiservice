package by.nichipor.taxiservice.service.orderpanel.controller;

import by.nichipor.taxiservice.entity.Location;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.service.ordermananger.OrderManager;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

/**
 * Created by Max Nichipor on 26.08.2016.
 */

@Controller
@RequestMapping("/order")
public class OrderPanelController {
    private static final String ORDER_PANEL_PAGE = "map";

    @Autowired
    OrderManager orderManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String showMap(Model ui){
        ui.addAttribute("placeOut", "&origin=Belarus");
        return ORDER_PANEL_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String makeOrder(@RequestParam("currentLat") Double currentLat,
                            @RequestParam("currentLng") Double currentLng,
                            @RequestParam("targetLat") Double targetLat,
                            @RequestParam("targetLng") Double targetLng,
                            @RequestParam("phone") String phone, Locale locale, Model ui){
        boolean errors = false;
        if (currentLat != null && currentLng != null){
            Location currentLocation = new Location(currentLat, currentLng);
            Location targetLocation;
            if (targetLat != null && targetLng != null) {
                targetLocation = new Location(targetLat, targetLng);
            } else {
                targetLocation = new Location(0,0);
            }
            if (orderManager.addOrder(new Order(UserManager.getCurrentUsername(), currentLocation, targetLocation, "+375" + phone))){
                ui.addAttribute("success", messageSource.getMessage("orderpanel.order_success", null, locale));
            } else {
                errors = true;
            }
        } else {
            errors = true;
        }
        if (errors){
            ui.addAttribute("error", messageSource.getMessage("orderpanel.order_error", null, locale));
        }
        ui.addAttribute("function", "<script>visible();</script>");
        return ORDER_PANEL_PAGE;
    }
}
