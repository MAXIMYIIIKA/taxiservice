package by.nichipor.taxiservice.service.usermanager.controller;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Max Nichipor on 11.08.2016.
 */

@Controller
@RequestMapping("/admin/usermanager**")
public class UserManagerController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private MessageSource messageSource;

    private User userForEdit;

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
    public String findUserById(@RequestParam("userId") String userId, Locale locale, Model ui){
        userManager.showUserById(ui, userId, locale);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"delete"}, method = RequestMethod.GET)
    public String deleteUser(@RequestParam("delete") String username, Locale locale, Model ui){
        User user = userManager.getUserByUsername(username);
        if (userManager.removeUser(user)) {
            ui.addAttribute("success", username + messageSource.getMessage("usrmanager.delete_success", null, locale));
        } else {
            ui.addAttribute("error", messageSource.getMessage("usrmanager.delete_error", null, locale));
        }
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"disable"}, method = RequestMethod.GET)
    public String disableUser(@RequestParam("disable") String username, Locale locale, Model ui){
        User user = userManager.getUserByUsername(username);
        userManager.disableUser(user);
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"enable"}, method = RequestMethod.GET)
    public String enableUser(@RequestParam("enable") String username, Locale locale, Model ui){
        User user = userManager.getUserByUsername(username);
        userManager.enableUser(user);
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"username", "password"}, method = RequestMethod.POST)
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password, Locale locale, Model ui){
        User user = new User(username, password);
        userManager.registerUser(user);
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"openEditForm"}, method = RequestMethod.GET)
    public String openEditForm(@RequestParam("openEditForm") String username, Locale locale, Model ui){
        ui.addAttribute("editForm", true);
        userForEdit = userManager.getUserByUsername(username);
        ui.addAttribute("editUser", userForEdit);
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"openRolesForm"}, method = RequestMethod.GET)
    public String openRolesForm(@RequestParam("openRolesForm") String username, Locale locale, Model ui){
        ui.addAttribute("rolesForm", true);
        userForEdit = userManager.getUserByUsername(username);
        ui.addAttribute("editUser", userForEdit);
        ui.addAttribute("AllRoles", Role.values());
        ui.addAttribute("userRoles", userManager.getUserRoles(userForEdit));
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"editUser"}, method = RequestMethod.POST)
    public String editUser(@RequestParam("newUsername") String newUsername,
                         @RequestParam("newPassword_1") String newPassword1,
                         @RequestParam("newPassword_2") String newPassword2, Locale locale, Model ui){
        boolean isEdited = false;
        boolean errors = false;
        if (!newUsername.equals(userForEdit.getUsername())) {
            isEdited = true;
            if (!userManager.changeUserName(userForEdit, newUsername)) {
                ui.addAttribute("error", messageSource.getMessage("usrmanager.username_duplicate_error", null, locale));
                errors = true;
                isEdited = false;
            }
        }
        if (newPassword1.length() > 0
                && newPassword2.length() > 0
                && newPassword1.equals(newPassword2)) {
            isEdited = true;
            if (!userManager.changeUserPassword(userForEdit, newPassword1)){
                ui.addAttribute("error", messageSource.getMessage("different_passwords_error", null, locale));
                errors = true;
                isEdited = false;
            }
        }
        if (!isEdited && !errors) {
            ui.addAttribute("success", messageSource.getMessage("usrmanager.no_changes", null, locale));
        } else if (isEdited && !errors) {
            ui.addAttribute("success", messageSource.getMessage("changes_saved", null, locale));
        }
        userForEdit = null;
        ui.addAttribute("editUser", null);
        ui.addAttribute("editForm", false);
        showAllUsers(ui);
        return "usermanager";
    }

    @RequestMapping(value = "", params = {"editRoles"}, method = RequestMethod.POST)
    public String editRoles(@RequestParam("roles") List<Role> roles, Locale locale, Model ui){
        if (userManager.getUserRoles(userForEdit).containsAll(roles) && roles.containsAll(userManager.getUserRoles(userForEdit))) {
            ui.addAttribute("success", messageSource.getMessage("usrmanager.no_changes", null, locale));
        } else {
            if (userManager.editRoles(userForEdit, roles)) {
                ui.addAttribute("success", messageSource.getMessage("changes_saved", null, locale));
            } else {
                ui.addAttribute("error", messageSource.getMessage("usrmanager.editing_error", null, locale));
            }
        }
        showAllUsers(ui);
        return "usermanager";
    }
}
