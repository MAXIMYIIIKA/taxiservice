package by.nichipor.taxiservice.database.DAO;

import by.nichipor.taxiservice.entity.Roles;
import by.nichipor.taxiservice.entity.User;

import java.util.List;

/**
 * Created by Max Nichipor on 09.08.2016.
 */
public interface UserDAO {
    List<User> findAllUsers();
    User findUserById(int id);
    boolean deleteUser(int id);
    boolean deleteUser(User user);
    boolean createUser(User user);
    boolean updateUser(User user);
    List<Roles> findUserRoles(User user);
    boolean addUserRole(User user, Roles role);
    boolean deleteUserRole(User user, Roles role);
}
