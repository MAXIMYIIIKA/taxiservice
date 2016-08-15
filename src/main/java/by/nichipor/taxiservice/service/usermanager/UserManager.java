package by.nichipor.taxiservice.service.usermanager;

import by.nichipor.taxiservice.database.dao.UserDAO;
import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Created by Max Nichipor on 10.08.2016.
 */

@Service
@ComponentScan("by.nichipor.taxiservice")
public class UserManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    MessageSource messageSource;

    public List<User> getAllUsers(){
        return userDAO.findAllUsers();
    }

    public Map<String, List<Role>> getAllRoles() {
        return userDAO.findAllRoles();
    }

    public boolean isExist(User user){
        if (user != null && (getUserByUsername(user.getUsername()) != null || getUserById(user.getUserId()) != null)) {
            return true;
        }
        return false;
    }

    public User getUserById(int userId){
        if (userId > 0) {
            return userDAO.findUserById(userId);
        }
        return null;
    }

    public User getUserByUsername(String username){
        if (username.length() >= 3) {
            return userDAO.findUserByUsername(username);
        }
        return null;
    }

    public boolean changeUserPassword(User user, String password){
        if (user != null && isExist(user)){
            user.setPassword(password);
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    public boolean changeUserName(User user, String username){
        if (username.length() > 0 && getUserByUsername(username) == null){
            List<Role> roles = new ArrayList<>();
            roles.addAll(userDAO.findUserRoles(user));
            userDAO.deleteAllUserRoles(user);
            user.setUsername(username);
            userDAO.updateUser(user);
            for (Role role: roles) {
                userDAO.addUserRole(user, role);
            }
            return true;
        }
        return false;
    }

    private boolean createUser(User user){
        if (!isExist(user) && user.getUsername().length() >= 3){
            userDAO.createUser(user);
            return true;
        }
        return false;
    }

    public boolean registerUser(User user){
        if (createUser(user) && addUserRole(user, Role.ROLE_USER)) {
            return true;
        }
        return false;
    }

    private boolean deleteUser(User user){
        if (isExist(user) && userDAO.deleteUser(user)){
            return true;
        }
        return false;
    }

    private boolean deleteAllUserRoles(User user){
        if (isExist(user) && userDAO.deleteAllUserRoles(user)) {
            return true;
        }
        return false;
    }

    public boolean removeUser(User user){
        deleteAllUserRoles(user);
        deleteUser(user);
        return true;
    }

    public List<Role> getUserRoles (User user) {
        if(isExist(user)){
            return userDAO.findUserRoles(user);
        }
        throw new NullPointerException("Invalid user");
    }

    public boolean hasRole(User user, Role role){
        if (isExist(user)){
            for (Role r: getUserRoles(user)){
                if (role.equals(r)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addUserRole(User user, Role role){
        if (isExist(user) && !hasRole(user, role)){
            userDAO.addUserRole(user, role);
            return true;
        }
        return false;
    }

    public boolean deleteUserRole(User user, Role role) {
        if (isExist(user) && hasRole(user, role)) {
            userDAO.deleteUserRole(user, role);
            return true;
        }
        return false;
    }

    public boolean editRoles(User user, List<Role> newRoles) {
        boolean errors = false;
        for (Role role: newRoles) {
            if (!getUserRoles(user).contains(role) && !userDAO.addUserRole(user, role)){
                errors = true;
            }
        }
        for (Role role: getUserRoles(user)) {
            if (!newRoles.contains(role) && !userDAO.deleteUserRole(user, role)) {
                errors = true;
            }
        }
        return errors;
    }

    public boolean disableUser(User user) {
        if (isExist(user) && user.isEnabled()) {
            user.setEnabled(false);
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    public boolean enableUser(User user) {
        if (isExist(user) && !user.isEnabled()) {
            user.setEnabled(true);
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    public void showUserById(Model ui, String userId, Locale locale){
        try {
            if (userId != null && userId.length() > 0 && Integer.valueOf(userId) > 0) {
                User gotUser = getUserById(Integer.valueOf(userId));
                if (isExist(gotUser)) {
                    List<User> users = new ArrayList<>();
                    Map<String, List<Role>> roles = new HashMap<>();
                    users.add(gotUser);
                    roles.put(gotUser.getUsername(), getUserRoles(gotUser));
                    ui.addAttribute("users", users);
                    ui.addAttribute("user_roles", roles);
                    ui.addAttribute("success", messageSource.getMessage("usrmanager.user_found", null, locale));
                } else {
                    ui.addAttribute("error", messageSource.getMessage("usrmanager.usr_not_found_error", null, locale));
                }
            } else {
                ui.addAttribute("error", messageSource.getMessage("usrmanager.usr_not_found_error", null, locale));
            }
        } catch (NumberFormatException e){
            ui.addAttribute("error", messageSource.getMessage("usrmanager.input_error", null, locale));
        }
    }
}
