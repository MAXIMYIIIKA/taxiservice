package by.nichipor.taxiservice.service.usermanager.controller;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import by.nichipor.taxiservice.service.usermanager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

/**
 * Created by Max Nichipor on 11.08.2016.
 */

@Controller
@RequestMapping("/admin/usermanager**")
public class UserManagerController {
    private static final String USER_MANANGER_PAGE = "usermanager";
    private static final String SUCCESS_VARIABLE = "success";
    private static final String ERROR_VARIABLE = "error";
    private static final String FUNCTION_VARIABLE = "function";
    private static final String FUNCTION_SCRIPT = "<script>visible();</script>";

    @Autowired
    private UserManager userManager;

    @Autowired
    private MessageSource messageSource;

    private User userForEdit;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userManagerMainPage(Model ui){
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"showAllUsers"}, method = RequestMethod.GET)
    public String showAllUsers(Model ui) {
        ui.addAttribute("users", userManager.findAllUsers());
        ui.addAttribute("user_roles", userManager.findAllRoles());
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"userId"}, method = RequestMethod.GET)
    public String findUserById(@RequestParam("userId") String userId, Locale locale, Model ui){
        userManager.showUserById(ui, userId, locale);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"delete"}, method = RequestMethod.GET)
    public String deleteUser(@RequestParam("delete") String username, Locale locale, Model ui){
        User user = userManager.findUserByUsername(username);
        if (userManager.removeUser(user)) {
            ui.addAttribute(SUCCESS_VARIABLE, username + messageSource.getMessage("usrmanager.delete_success", null, locale));
        } else {
            ui.addAttribute(ERROR_VARIABLE, messageSource.getMessage("usrmanager.delete_error", null, locale));
        }
        ui.addAttribute(FUNCTION_VARIABLE, FUNCTION_SCRIPT);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"disable"}, method = RequestMethod.GET)
    public String disableUser(@RequestParam("disable") String username, Locale locale, Model ui){
        User user = userManager.findUserByUsername(username);
        userManager.disableUser(user);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"enable"}, method = RequestMethod.GET)
    public String enableUser(@RequestParam("enable") String username, Locale locale, Model ui){
        User user = userManager.findUserByUsername(username);
        userManager.enableUser(user);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"username", "password"}, method = RequestMethod.POST)
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password, Locale locale, Model ui){
        User user = new User(username, password);
        userManager.registerUser(user);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"openEditForm"}, method = RequestMethod.GET)
    public String openEditForm(@RequestParam("openEditForm") String username, Locale locale, Model ui){
        ui.addAttribute("editForm", true);
        userForEdit = userManager.findUserByUsername(username);
        ui.addAttribute("editUser", userForEdit);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"openRolesForm"}, method = RequestMethod.GET)
    public String openRolesForm(@RequestParam("openRolesForm") String username, Locale locale, Model ui){
        ui.addAttribute("rolesForm", true);
        userForEdit = userManager.findUserByUsername(username);
        ui.addAttribute("editUser", userForEdit);
        ui.addAttribute("AllRoles", Role.values());
        ui.addAttribute("userRoles", userManager.getUserRoles(userForEdit));
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
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
                ui.addAttribute(ERROR_VARIABLE, messageSource.getMessage("usrmanager.username_duplicate_error", null, locale));
                errors = true;
                isEdited = false;
            }
        }
        if (newPassword1.length() > 0
                && newPassword2.length() > 0
                && newPassword1.equals(newPassword2)) {
            isEdited = true;
            if (!userManager.changeUserPassword(userForEdit, newPassword1)){
                ui.addAttribute(ERROR_VARIABLE, messageSource.getMessage("different_passwords_error", null, locale));
                errors = true;
                isEdited = false;
            }
        }
        if (!isEdited && !errors) {
            ui.addAttribute(SUCCESS_VARIABLE, messageSource.getMessage("usrmanager.no_changes", null, locale));
        } else if (isEdited && !errors) {
            ui.addAttribute(SUCCESS_VARIABLE, messageSource.getMessage("changes_saved", null, locale));
        }
        ui.addAttribute(FUNCTION_VARIABLE, FUNCTION_SCRIPT);
        userForEdit = null;
        ui.addAttribute("editUser", null);
        ui.addAttribute("editForm", false);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }

    @RequestMapping(value = "", params = {"editRoles"}, method = RequestMethod.POST)
    public String editRoles(@RequestParam("roles") List<Role> roles, Locale locale, Model ui){
        if (userManager.getUserRoles(userForEdit).containsAll(roles) && roles.containsAll(userManager.getUserRoles(userForEdit))) {
            ui.addAttribute(SUCCESS_VARIABLE, messageSource.getMessage("usrmanager.no_changes", null, locale));
        } else {
            if (userManager.editRoles(userForEdit, roles)) {
                ui.addAttribute(SUCCESS_VARIABLE, messageSource.getMessage("changes_saved", null, locale));
            } else {
                ui.addAttribute(ERROR_VARIABLE, messageSource.getMessage("usrmanager.editing_error", null, locale));
            }
        }
        ui.addAttribute(FUNCTION_VARIABLE, FUNCTION_SCRIPT);
        showAllUsers(ui);
        return USER_MANANGER_PAGE;
    }
}
