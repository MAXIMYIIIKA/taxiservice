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
    private static final String LOGIN_PAGE = "login";

    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(Model ui){
        return LOGIN_PAGE;
    }

    @RequestMapping(params = {"logout"},method = RequestMethod.GET)
    public void logoutSuccess(Locale locale, Model ui){
        ui.addAttribute("success", messageSource.getMessage("login.logout", null, locale));
        ui.addAttribute("function", "<script>visible();</script>");
        loginPage(ui);
    }

    @RequestMapping(params = {"error"},method = RequestMethod.GET)
    public void loginError(Locale locale, Model ui){
        ui.addAttribute("error", messageSource.getMessage("login.error", null, locale));
        ui.addAttribute("function", "<script>visible();</script>");
        loginPage(ui);
    }
}
