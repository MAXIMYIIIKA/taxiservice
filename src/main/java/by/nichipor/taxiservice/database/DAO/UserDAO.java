package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Max Nichipor on 09.08.2016.
 */
public interface UserDAO {
    List<User> findAllUsers();
    Map<String,List<Role>> findAllRoles();
    User findUserById(int id);
    User findUserByUsername(String username);
    boolean deleteUser(User user);
    boolean createUser(User user);
    boolean updateUser(User user);
    List<Role> findUserRoles(User user);
    boolean addUserRole(User user, Role role);
    boolean deleteUserRole(User user, Role role);
    boolean deleteAllUserRoles(User user);
}
