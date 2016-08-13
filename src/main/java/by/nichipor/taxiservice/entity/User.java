package by.nichipor.taxiservice.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Component
public class User {
    private int userId;
    private String username;
    private String password;
    private boolean enabled;

    private PasswordEncoder passwordEncoder;

    public User(){}

    public User(String username, String password){
        setUserId(-1);
        setUsername(username);
        setPassword(password);
    }

    public User(int userId, String username, String password, boolean enabled){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
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
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
