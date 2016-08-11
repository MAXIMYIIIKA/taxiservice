package by.nichipor.taxiservice.services.usermanager;

import by.nichipor.taxiservice.database.DAO.UserDAO;
import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max Nichipor on 10.08.2016.
 */

@Service
@ComponentScan("by.nichipor.taxiservice")
public class UserManager {

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public Map<String, List<Role>> getAllRoles() {
        return userDAO.getAllRoles();
    }

    public boolean isExist(User user){
        if (user != null && (getUserByUsername(user.getUsername()) != null || getUserById(user.getUserId()) != null)) {
            return true;
        }
        return false;
    }

    public User getUserById(int UserId){
        if (UserId > 0) {
            return userDAO.getUserById(UserId);
        }
        return null;
    }

    public User getUserByUsername(String username){
        if (username.length() >= 3) {
            return userDAO.getUserByUsername(username);
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

    public boolean createUser(User user){
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

    public boolean deleteUser(User user){
        if (isExist(user) && userDAO.deleteUser(user)){
            return true;
        }
        return false;
    }

    public boolean deleteAllUserRoles(User user){
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
            return userDAO.getUserRoles(user);
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

    public void showUserById(Model ui, String userId){
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
                    ui.addAttribute("success", "User found!");
                } else {
                    ui.addAttribute("error", "User not found!");
                }
            } else {
                ui.addAttribute("error", "User not found!");
            }
        } catch (NumberFormatException e){
            ui.addAttribute("error", "Use only numbers, please!");
        }
    }
}
