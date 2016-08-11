package by.nichipor.taxiservice.services.usermanager.controllers;

import by.nichipor.taxiservice.services.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Max Nichipor on 11.08.2016.
 */

@Controller
@RequestMapping("/admin/usermanager**")
public class UserManagerController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userManagerMainPage(){
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"showAllUsers"}, method = RequestMethod.GET)
    public String showAllUsers(Model ui) {
        ui.addAttribute("users", userManager.getAllUsers());
        ui.addAttribute("user_roles", userManager.getAllRoles());
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"userId"}, method = RequestMethod.GET)
    public String findUserById(@RequestParam("userId") String userId, Model ui){
        userManager.showUserById(ui, userId);
        return "usermanager";
    }

}
