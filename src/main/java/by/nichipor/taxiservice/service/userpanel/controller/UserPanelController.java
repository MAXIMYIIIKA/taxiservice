package by.nichipor.taxiservice.service.userpanel.controller;

import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max Nichipor on 05.09.2016.
 */

@Controller
@RequestMapping("/user")
public class UserPanelController {
    private static final String USER_PANEL_PAGE = "user";

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String userPage(Model ui){
        User user = userManager.findUserByUsername(UserManager.getCurrentUsername());
        ui.addAttribute("numberOfUserOrders", userManager.findNumberOfUserOrders(user));
        ui.addAttribute("orders", userManager.findAllAcceptedUserOrders(user));
        return USER_PANEL_PAGE;
    }
}
