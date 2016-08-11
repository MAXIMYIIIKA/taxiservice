package by.nichipor.taxiservice.services.adminpanel.controllers;

import by.nichipor.taxiservice.services.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Max Nichipor on 10.08.2016.
 */

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminPage(Model ui) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        ui.addAttribute("user", username);
        return "admin";
    }
}
