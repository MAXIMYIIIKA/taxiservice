package by.nichipor.taxiservice.service.orderpanel.controller;

import by.nichipor.taxiservice.entity.Location;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.service.notifier.Notifier;
import by.nichipor.taxiservice.service.ordermananger.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Max Nichipor on 26.08.2016.
 */

@Controller
@RequestMapping("/order")
public class OrderPanelController {

    @Autowired
    OrderManager orderManager;

//    @Autowired
//    private Notifier notifier;

    @RequestMapping(method = RequestMethod.GET)
    public String showMap(Model ui){
        ui.addAttribute("placeOut", "&origin=Belarus");
        return "map";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String makeOrder(@RequestParam("currentLat") Double currentLat,
                          @RequestParam("currentLng") Double currentLng,
                          @RequestParam("targetLat") Double targetLat,
                          @RequestParam("targetLng") Double targetLng,
                            @RequestParam("phone") String phone, Model ui){
        Location currentLocation = new Location(currentLat, currentLng);
        Location targetLocation;
        if (targetLat != null && targetLng != null) {
            targetLocation = new Location(targetLat, targetLng);
        } else {
            targetLocation = new Location(0,0);
        }
        orderManager.addOrder(new Order(getCurrentUsername(), currentLocation, targetLocation, "+375" + phone));
//        notifier.addNewOrderNotification();
        return "map";
    }

    private String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
