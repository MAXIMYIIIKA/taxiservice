package by.nichipor.taxiservice.controller;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping({"", "/", "/main"})
public class MainPageController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(Locale locale, Model ui){
        ui.addAttribute("msg", messageSource.getMessage("main.msg", null, locale));
        return "main";
    }
}
