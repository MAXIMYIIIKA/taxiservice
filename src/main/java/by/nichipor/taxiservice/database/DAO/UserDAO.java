package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

/**
 *  This interface describes the user data access object layer.
 *  @author Max Nichipor
 */
public interface UserDAO {
    List<User> findAllUsers() throws InterruptedException;
    Map<String,List<Role>> findAllRoles() throws InterruptedException;
    User findUserById(int id) throws InterruptedException;
    User findUserByUsername(String username) throws InterruptedException;
    boolean deleteUser(User user) throws InterruptedException;
    boolean createUser(User user) throws InterruptedException;
    boolean updateUser(User user) throws InterruptedException;
    List<Role> findUserRoles(User user) throws InterruptedException;
    boolean addUserRole(User user, Role role) throws InterruptedException;
    boolean deleteUserRole(User user, Role role) throws InterruptedException;
    boolean deleteAllUserRoles(User user) throws InterruptedException;
}
