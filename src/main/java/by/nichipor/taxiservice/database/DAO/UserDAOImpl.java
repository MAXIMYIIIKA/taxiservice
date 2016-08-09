package by.nichipor.taxiservice.database.DAO;

import by.nichipor.taxiservice.entity.Roles;
import by.nichipor.taxiservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private DataSource dataSource;

    public List<User> findAllUsers() {
        throw new UnsupportedOperationException();
    }

    public User findUserById(int id) {
        throw new UnsupportedOperationException();
    }

    public List<Roles> findRoles(User user) {
        throw new UnsupportedOperationException();
    }

    public boolean deleteUser(int id) {
        throw new UnsupportedOperationException();
    }

    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException();
    }

    public boolean createUser(User user) {
        throw new UnsupportedOperationException();
    }

    public boolean updateUser(User user) {
        throw new UnsupportedOperationException();
    }
}
