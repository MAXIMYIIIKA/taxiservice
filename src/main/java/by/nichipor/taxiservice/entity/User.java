package by.nichipor.taxiservice.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

/**
 * This is a user instance.
 */
@Component
public class User {
    private int userId;
    private String username;
    private String password;
    private boolean enabled;

    public User(){
        //This default constructor is empty because of the Spring Bean Autowiring
    }

    public User(String username, String password){
        setUserId(-1);
        setUsername(username);
        setPassword(password);
    }

    /**
     * This constructor creates the user object to be filled from the database.
     * @param userId user's ID.
     * @param username user's name.
     * @param password  user's password.
     * @param enabled user's status.
     */
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
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (password != null) {
            this.password = passwordEncoder.encode(password);
        } else {
            this.password = passwordEncoder.encode("temporary");
        }
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (getUserId() != user.getUserId()) {
            return false;
        }
        return getUsername().equals(user.getUsername());

    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + getUsername().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
