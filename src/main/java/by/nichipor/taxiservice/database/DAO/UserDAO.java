package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;

import java.io.InputStream;
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
    /**
     * This method is used to list all users from database.
     * @return a list of users.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<User> findAllUsers() throws InterruptedException;

    /**
     * This method is used to list all user roles from database.
     * @return a map where the key is a username and
     * the value is a list of this user roles.
     * @throws InterruptedException if interrupted while getting connection.
     */
    Map<String,List<Role>> findAllRoles() throws InterruptedException;

    /**
     * This method is used to find user in database by specified ID.
     * @param id user's ID.
     * @return a user, or null if there is no users with such ID.
     * @throws InterruptedException if interrupted while getting connection.
     */
    User findUserById(int id) throws InterruptedException;

    /**
     * This method is used to find user in database by specified username.
     * @param username
     * @return a user, or null if there is no users with such username.
     * @throws InterruptedException if interrupted while getting connection.
     */
    User findUserByUsername(String username) throws InterruptedException;

    /**
     * This method is used to delete user from database.
     * @param user
     * @return true if deletion was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteUser(User user) throws InterruptedException;

    /**
     * This method is used to create and add user to the database.
     * @param user
     * @return true if creation and adding to the database was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean createUser(User user) throws InterruptedException;

    /**
     * This method is used to update user in the database.
     * @param user
     * @return true if updating was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean updateUser(User user) throws InterruptedException;

    /**
     * This method is used to find all roles of the specified user.
     * @param user
     * @return a list of roles.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Role> findUserRoles(User user) throws InterruptedException;

    /**
     * This method is used to add user role to the database.
     * @param user the user whose role we want to add.
     * @param role the role for adding.
     * @return true if adding was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean addUserRole(User user, Role role) throws InterruptedException;

    /**
     * This method is used to delete specified user's role from the database.
     * @param user the user whose role we want to delete.
     * @param role the role for deleting.
     * @return true if deleting from the database was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteUserRole(User user, Role role) throws InterruptedException;

    /**
     * This method is used to delete all specified user's roles from the database.
     * @param user the user whose roles we want to delete.
     * @return true if deleting from the database was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteAllUserRoles(User user) throws InterruptedException;

    boolean addAvatar(String username, InputStream inputStream) throws InterruptedException;
    byte[] findAvatar(String username) throws InterruptedException;
    boolean hasAvatar(String username) throws InterruptedException;
    boolean updateAvatar(String username, InputStream inputStream) throws InterruptedException;
    boolean deleteAvatar(String username) throws InterruptedException;
}
