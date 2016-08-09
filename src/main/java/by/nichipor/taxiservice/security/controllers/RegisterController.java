package by.nichipor.taxiservice.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage(Model ui) {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password, Model ui) {
        ui.addAttribute("error", "Username: " + username + "\nPassword: " + encoder.encode(password));
        return "register";
    }
}
