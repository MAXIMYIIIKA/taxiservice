package by.nichipor.taxiservice.security.controller;

import by.nichipor.taxiservice.entity.User;
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
 * Created by Max Nichipor on 09.08.2016.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage(Model ui) {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Locale locale,
                               Model ui) {
        User user = new User(username, password);
        if (userManager.registerUser(user)){
            ui.addAttribute("success", username
                                        + messageSource.getMessage("register.success", null, locale));
        } else {
            ui.addAttribute("error", messageSource.getMessage("register.error", null, locale));
        }
        return "register";
    }
}
