package by.nichipor.taxiservice.entity;

import by.nichipor.taxiservice.database.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Component
public class User implements Comparator<User>{
    private int userId;
    private String username;
    private String password;
    private List<Roles> roles;
    private boolean enabled;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    public User(){
        this.roles = new ArrayList<>();
    }

    public User(String username, String password){
        setUserId(-1);
        setUsername(username);
        setPassword(password);
        setEnabled(true);
        roles = new ArrayList<>();
        roles.add(Roles.user);
    }

    public User(int userId, String username, String password, boolean enabled, List<Roles> roles){
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setEnabled(enabled);
        setRoles(roles);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
            this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = new ArrayList<>();
        for (Roles role: roles){
            this.roles.add(role);
        }
    }

    public boolean hasRole(Roles checkRole){
        for (Roles role: this.roles){
            if (role.equals(checkRole)){
                return true;
            }
        }
        return false;
    }

    public boolean addRole (Roles role){
        if (!hasRole(role)) {
            this.roles.add(role);
            return true;
        }
        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExist(){
        for (User user : userDAO.findAllUsers()) {
            if (compare(this, user) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean update(){
        if (this.username != null && this.password != null) {
            if (this.userId == -1 && !isExist()) {
                this.userDAO.createUser(this);
                return true;
            } else if (isExist()){
                userDAO.updateUser(this);
                return true;
            }
        }
        return false;
    }

    public boolean delete(){
        if (isExist()){
            userDAO.deleteUser(this);
            return true;
        }
        return false;
    }

    @Override
    public int compare(User o1, User o2) {
        if (o1.getUsername().equals(o2.getUsername())) {
            return 0;
        }
        return -1;
    }
}
