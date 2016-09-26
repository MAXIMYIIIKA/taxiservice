package by.nichipor.taxiservice.service.adminpanel.controller;

import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max Nichipor on 10.08.2016.
 */

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    private static final String ADMIN_PAGE = "admin";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminPage(Model ui) {
        ui.addAttribute("user", UserManager.getCurrentUsername());
        return ADMIN_PAGE;
    }
}
