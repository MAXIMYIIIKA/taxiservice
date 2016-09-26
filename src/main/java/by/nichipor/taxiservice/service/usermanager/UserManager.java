package by.nichipor.taxiservice.service.usermanager;

import by.nichipor.taxiservice.database.dao.OrderDAO;
import by.nichipor.taxiservice.database.dao.UserDAO;
import by.nichipor.taxiservice.entity.Order;
import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Created by Max Nichipor on 10.08.2016.
 */

@Service
@ComponentScan("by.nichipor.taxiservice")
public class UserManager {

    private static Logger logger = Logger.getLogger(UserManager.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    MessageSource messageSource;

    public List<User> findAllUsers() {
        try {
            return userDAO.findAllUsers();
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return new ArrayList<>();
    }

    public Map<String, List<Role>> findAllRoles() {
        try {
            return userDAO.findAllRoles();
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return new HashMap<>();
    }

    public boolean isExist(User user) {
        if (user != null && (findUserByUsername(user.getUsername()) != null || findUserById(user.getUserId()) != null)) {
            return true;
        }
        return false;
    }

    public User findUserById(int userId) {
        try {
            if (userId > 0) {
                return userDAO.findUserById(userId);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return null;
    }

    public User findUserByUsername(String username) {
        try {
            if (username.length() >= 3) {
                return userDAO.findUserByUsername(username);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return null;
    }

    public boolean changeUserPassword(User user, String password) {
        try {
            if (user != null && isExist(user)) {
                user.setPassword(password);
                userDAO.updateUser(user);
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean changeUserName(User user, String username) {
        try {
            if (username.length() > 0 && findUserByUsername(username) == null) {
                List<Role> roles = new ArrayList<>();
                roles.addAll(userDAO.findUserRoles(user));
                userDAO.deleteAllUserRoles(user);
                user.setUsername(username);
                userDAO.updateUser(user);
                for (Role role : roles) {
                    userDAO.addUserRole(user, role);
                }
                return true;
            }
        } catch (InterruptedException e) {
           logger.error(e);
        }
        return false;
    }

    private boolean createUser(User user) {
        try {
            if (!isExist(user) && user.getUsername().length() >= 3) {
                userDAO.createUser(user);
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean registerUser(User user) {
        if (!isExist(user) && createUser(user) && addUserRole(user, Role.ROLE_USER)) {
            return true;
        }
        return false;
    }

    private boolean deleteUser(User user) {
        try {
            if (isExist(user) && userDAO.deleteUser(user)){
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    private boolean deleteAllUserRoles(User user) {
        try {
            if (isExist(user) && userDAO.deleteAllUserRoles(user)) {
                return true;
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean removeUser(User user) {
        return deleteAllUserRoles(user) && deleteUser(user);
    }

    public List<Role> getUserRoles (User user) {
        if(isExist(user)){
            try {
                return userDAO.findUserRoles(user);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return new ArrayList<>();
    }

    public boolean hasRole(User user, Role role) {
        if (isExist(user)){
            for (Role r: getUserRoles(user)){
                if (role.equals(r)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addUserRole(User user, Role role) {
        if (isExist(user) && !hasRole(user, role)){
            try {
                userDAO.addUserRole(user, role);
                return true;
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return false;
    }

    public boolean deleteUserRole(User user, Role role) {
        if (isExist(user) && hasRole(user, role)) {
            try {
                userDAO.deleteUserRole(user, role);
                return true;
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return false;
    }

    public boolean editRoles(User user, List<Role> newRoles) {
        boolean errors = false;
        for (Role role: newRoles) {
            try {
                if (!getUserRoles(user).contains(role) && !userDAO.addUserRole(user, role)){
                    errors = true;
                }
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        for (Role role: getUserRoles(user)) {
            try {
                if (!newRoles.contains(role) && !userDAO.deleteUserRole(user, role)) {
                    errors = true;
                }
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return !errors;
    }

    public boolean disableUser(User user) {
        if (isExist(user) && user.isEnabled()) {
            try {
                user.setEnabled(false);
                userDAO.updateUser(user);
                return true;
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return false;
    }

    public boolean enableUser(User user) {
        if (isExist(user) && !user.isEnabled()) {
            try {
                user.setEnabled(true);
                userDAO.updateUser(user);
                return true;
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return false;
    }

    public void showUserById(Model ui, String userId, Locale locale){
        try {
            if (userId != null && userId.length() > 0 && Integer.parseInt(userId) > 0) {
                User gotUser = findUserById(Integer.parseInt(userId));
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

    public int findNumberOfUserOrders(User user) {
        if (user != null && isExist(user)) {
            try {
                return orderDAO.findNumberOfUserOrders(user);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return 0;
    }

    public List<Order> findAllAcceptedUserOrders(User user) {
        if (isExist(user)) {
            try {
                return orderDAO.findAllAcceptedUserOrders(user);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return new ArrayList<>();
    }

    public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
