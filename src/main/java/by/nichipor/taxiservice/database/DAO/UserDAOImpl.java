package by.nichipor.taxiservice.database.dao;

import by.nichipor.taxiservice.database.config.DBConnPool;
import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Component
public class UserDAOImpl implements UserDAO {

    private static Logger logger = Logger.getLogger(UserDAOImpl.class);

    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_SELECT_ALL_USER_ROLES = "SELECT * FROM user_roles";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE userId = ?";
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_SELECT_USER_ROLES = "SELECT * FROM user_roles WHERE username = ?";
    private static final String SQL_INSERT_USER_ROLE = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
    private static final String SQL_DELETE_USER_ROLE = "DELETE FROM user_roles WHERE username = ? AND role = ?";
    private static final String SQL_DELETE_ALL_USER_ROLES = "DELETE FROM user_roles WHERE username = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET username = ?, password = ?, enabled = ? WHERE userId = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE username = ? AND userId = ?";
    private static final String SQL_INSERT_IMAGE = "INSERT INTO avatars (username, image) VALUES (?, ?)";
    private static final String SQL_SELECT_IMAGE = "SELECT * FROM avatars WHERE username = ?";
    private static final String SQL_COUNT_IMAGE = "SELECT COUNT(*) FROM avatars WHERE username = ?";
    private static final String SQL_UPDATE_IMAGE = "UPDATE avatars SET image = ? WHERE username = ?";
    private static final String SQL_DELETE_IMAGE = "DELETE FROM avatars WHERE username = ?";
    private static final String USER_ID_FIELD = "userId";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String ENABLED_FIELD = "enabled";
    private static final String ROLE_FIELD = "role";
    private static final String IMAGE_FIELD = "image";
    private static final int BUFFER_SIZE = 4096;


    @Autowired
    private DBConnPool dbConnPool;

    @Override
    public List<User> findAllUsers() throws InterruptedException{
        List<User> users = new ArrayList<>();
        User tempUser;
        Connection connection = dbConnPool.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                tempUser = new User(resultSet.getInt(USER_ID_FIELD),
                        resultSet.getString(USERNAME_FIELD),
                        resultSet.getString(PASSWORD_FIELD),
                        resultSet.getBoolean(ENABLED_FIELD));
                users.add(tempUser);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return users;
    }

    @Override
    public Map<String,List<Role>> findAllRoles() throws InterruptedException {
        Map<String,List<Role>> roles = new HashMap<>();
        Connection connection = dbConnPool.getConnection();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER_ROLES)){
            while (resultSet.next()) {
                if (roles.containsKey(resultSet.getString(USERNAME_FIELD))) {
                    roles.get(resultSet.getString(USERNAME_FIELD)).add(Role.valueOf(resultSet.getString(ROLE_FIELD)));
                } else {
                    roles.put(resultSet.getString(USERNAME_FIELD), new ArrayList<>());
                    roles.get(resultSet.getString(USERNAME_FIELD)).add(Role.valueOf(resultSet.getString(ROLE_FIELD)));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return roles;
    }

    @Override
    public User findUserById(int userId) throws InterruptedException{
        User user = null;
        ResultSet resultSet;
        Connection connection = dbConnPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)){
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt(USER_ID_FIELD),
                            resultSet.getString(USERNAME_FIELD),
                            resultSet.getString(PASSWORD_FIELD),
                            resultSet.getBoolean(ENABLED_FIELD));
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) throws InterruptedException{
        User user = null;
        ResultSet resultSet;
        Connection connection = dbConnPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME)){
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt(USER_ID_FIELD),
                    resultSet.getString(USERNAME_FIELD),
                    resultSet.getString(PASSWORD_FIELD),
                    resultSet.getBoolean(ENABLED_FIELD));
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return user;
    }

    @Override
    public List<Role> findUserRoles(User user) throws InterruptedException{
        List<Role> roles = new ArrayList<>();
        ResultSet resultSet;
        Connection connection = dbConnPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ROLES)){
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                roles.add(Role.valueOf(resultSet.getString(ROLE_FIELD)));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return roles;
    }

    /**
     * This method is used to update(create, delete, change) specified user's specified role.
     * <p>
     *     Which one of the options will be used is depends on the SQL query.
     * </p>
     * @param user the user whose role we want to update.
     * @param role the role which we want to update.
     * @param sqlQuery SQL query for updating the role.
     * @return true if the SQL query executed successfully; false if it is not.
     * @throws InterruptedException if interrupted while getting connection.
     */
    private boolean updateUserRole(User user, Role role, String sqlQuery) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, role.name());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean addUserRole(User user, Role role) throws InterruptedException {
        return updateUserRole(user, role, SQL_INSERT_USER_ROLE);
    }

    @Override
    public boolean deleteUserRole(User user, Role role) throws InterruptedException {
        return updateUserRole(user, role, SQL_DELETE_USER_ROLE);
    }

    @Override
    public boolean deleteAllUserRoles(User user) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ALL_USER_ROLES)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean createUser(User user) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            if (user.isEnabled()) {
                preparedStatement.setInt(3, 1);
            } else {
                preparedStatement.setInt(3, 0);
            }
            preparedStatement.setInt(4, user.getUserId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean addAvatar(String username, InputStream inputStream) throws InterruptedException{
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_IMAGE)){
            preparedStatement.setString(1, username);
            preparedStatement.setBlob(2, inputStream);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public byte[] findAvatar(String username) throws InterruptedException{
        byte[] avatar;
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_IMAGE)){
            preparedStatement.setString(1, username);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte [] buffer = new byte[BUFFER_SIZE];
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(IMAGE_FIELD);
                InputStream inputStream = blob.getBinaryStream();
                while (inputStream.read(buffer, 0, buffer.length) != -1) {
                    outputStream.write(buffer, 0, buffer.length);
                }
                avatar = outputStream.toByteArray();
                return avatar;
            }
        } catch (SQLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return new byte[1];
    }

    @Override
    public boolean hasAvatar(String username) throws InterruptedException {
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_IMAGE)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean updateAvatar(String username, InputStream inputStream) throws InterruptedException {
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_IMAGE)){
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteAvatar(String username) throws InterruptedException {
        Connection connection = dbConnPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_IMAGE)){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbConnPool.putConnection(connection);
        }
        return false;
    }


}
