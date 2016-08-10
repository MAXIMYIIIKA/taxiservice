package by.nichipor.taxiservice.controllers;

import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage(Model ui) {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password, Model ui) {
        User user = new User(username, password);
        if (userManager.registerUser(user)){
            ui.addAttribute("success", username + " registered successfully");
        } else {
            ui.addAttribute("error", "Registration faild!");
        }
        return "register";
    }
}
