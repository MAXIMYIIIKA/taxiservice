package by.nichipor.taxiservice.controllers;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.services.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping({"", "/", "/main"})
public class MainPageController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(Model ui){
        ui.addAttribute("msg", "This is the MAIN PAGE");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userManager.getUserByUsername(username);
        if (user != null && (userManager.getUserRoles(user).contains(Role.ROLE_USER)
                                || userManager.getUserRoles(user).contains(Role.ROLE_ADMIN))) {
            ui.addAttribute("logout_display", "inline");
            ui.addAttribute("login_display", "none");
        } else {
            ui.addAttribute("logout_display", "none");
            ui.addAttribute("login_display", "inline");
        }
        return "main";
    }
}
