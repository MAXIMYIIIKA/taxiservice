package by.nichipor.taxiservice.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(Model ui){
        return "login";
    }

    @RequestMapping(params = {"logout"},method = RequestMethod.GET)
    public String logoutSuccess(Model ui){
        ui.addAttribute("logout", "Logout is successful");
        return "login";
    }

    @RequestMapping(params = {"error"},method = RequestMethod.GET)
    public String loginError(Model ui){
        ui.addAttribute("error", "Invalid username or password!");
        return "login";
    }
}
