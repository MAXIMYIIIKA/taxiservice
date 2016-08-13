package by.nichipor.taxiservice.service.adminpanel.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
