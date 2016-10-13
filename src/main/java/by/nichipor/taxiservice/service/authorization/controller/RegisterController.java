package by.nichipor.taxiservice.service.authorization.controller;

import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {
    private static final String REGISTER_PAGE = "register";

    @Autowired
    MessageSource messageSource;

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage(Model ui) {
        return REGISTER_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("confPassword") String confPassword,
                               Locale locale,
                               Model ui) {
        User user = new User(username, password);
        if (password.equals(confPassword) && userManager.registerUser(user)) {
            ui.addAttribute("success", username + messageSource.getMessage("register.success", null, locale));
        } else {
            ui.addAttribute("error", messageSource.getMessage("register.error", null, locale));
        }
        ui.addAttribute("function", "<script>visible();</script>");
        return REGISTER_PAGE;
    }

    @RequestMapping(value = "passwordsDontMatchMessage", method = RequestMethod.GET,
            produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getPasswordDontMatchErrorMessage(){
        return messageSource.getMessage("register.passwords_dont_match", null, RequestContextUtils.getLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
    }

    @RequestMapping(value = "emptyUsername", method = RequestMethod.GET,
            produces = "text/html; charset=utf-8")
    @ResponseBody
    public String emptyUsernameErrorMessage(){
        return messageSource.getMessage("invalid_username", null, RequestContextUtils.getLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
    }
}
