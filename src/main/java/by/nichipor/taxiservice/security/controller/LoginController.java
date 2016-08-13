package by.nichipor.taxiservice.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(Model ui){
        return "login";
    }

    @RequestMapping(params = {"logout"},method = RequestMethod.GET)
    public String logoutSuccess(Locale locale, Model ui){
        ui.addAttribute("logout", messageSource.getMessage("login.logout", null, locale));
        return "login";
    }

    @RequestMapping(params = {"error"},method = RequestMethod.GET)
    public String loginError(Locale locale, Model ui){
        ui.addAttribute("error", messageSource.getMessage("login.error", null, locale));
        return "login";
    }
}
