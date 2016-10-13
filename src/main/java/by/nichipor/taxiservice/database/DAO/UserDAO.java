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
     * Returns a list of all {@link User users} from database.
     * @return a list of {@link User users}.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<User> findAllUsers() throws InterruptedException;

    /**
     * Returns a map of all user {@link Role roles} from database.
     * @return a map where the key is a username and
     * the value is a list of this user roles.
     * @throws InterruptedException if interrupted while getting connection.
     */
    Map<String,List<Role>> findAllRoles() throws InterruptedException;

    /**
     * Finds user in database by specified id.
     * @param id user's id.
     * @return a {@link User user}, or null if there is no users with such id.
     * @throws InterruptedException if interrupted while getting connection.
     */
    User findUserById(int id) throws InterruptedException;

    /**
     * Finds user in database by specified username.
     * @param username the name of the user.
     * @return a {@link User user}, or null if there is no users with the such username.
     * @throws InterruptedException if interrupted while getting connection.
     */
    User findUserByUsername(String username) throws InterruptedException;

    /**
     * Deletes the specified {@link User user} from the database.
     * @param user a {@link User user} for deleting.
     * @return true if deletion was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteUser(User user) throws InterruptedException;

    /**
     * Creates and adds {@link User user} to the database.
     * @param user a {@link User user} for adding.
     * @return true if creation and adding to the database was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean createUser(User user) throws InterruptedException;

    /**
     * Updates {@link User user} in the database.
     * @param user a {@link User user} for updating.
     * @return true if updating was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean updateUser(User user) throws InterruptedException;

    /**
     * Finds all {@link Role roles} of the specified {@link User user}.
     * @param user a {@link User user} whose roles we need.
     * @return a list of roles.
     * @throws InterruptedException if interrupted while getting connection.
     */
    List<Role> findUserRoles(User user) throws InterruptedException;

    /**
     * Adds the specified user's {@link Role role} to the database.
     * @param user the {@link User user} whose {@link Role role} we want to add.
     * @param role the {@link Role role} for adding.
     * @return true if adding was successful; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean addUserRole(User user, Role role) throws InterruptedException;

    /**
     * Deletes specified user's {@link Role role} from the database.
     * @param user the {@link User user} whose {@link Role role} we want to delete.
     * @param role the {@link Role role} for deleting.
     * @return true if deleting from the database was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteUserRole(User user, Role role) throws InterruptedException;

    /**
     * Deletes all specified user's {@link Role roles} from the database.
     * @param user the {@link User user} whose {@link Role roles} we want to delete.
     * @return true if deleting from the database was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteAllUserRoles(User user) throws InterruptedException;

    /**
     * Inserts the specified image of the specified {@link User user} into the database.
     * @param username the name of the {@link User user}.
     * @param inputStream an inputStream.
     * @return true if adding to the database was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean addAvatar(String username, InputStream inputStream) throws InterruptedException;

    /**
     * Finds an image of the specified {@link User user} in the database.
     * @param username the name of the {@link User user}.
     * @return an image as a byte array or an empty byte array.
     * @throws InterruptedException if interrupted while getting connection.
     */
    byte[] findAvatar(String username) throws InterruptedException;

    /**
     * Returns true if there is an avatar of the specified {@link User user} in the database.
     * @param username the name of the {@link User user}.
     * @return true if there is an avatar int the database;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean hasAvatar(String username) throws InterruptedException;

    /**
     * Updates the specified user's avatar in the database.
     * @param username the name of the {@link User user}.
     * @param inputStream an inputStream.
     * @return true if updating was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean updateAvatar(String username, InputStream inputStream) throws InterruptedException;

    /**
     * Deletes the specified user's avatar from the database.
     * @param username the name of the {@link User user}.
     * @return true if deleting was successful;
     * false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    boolean deleteAvatar(String username) throws InterruptedException;
}
