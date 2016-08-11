package by.nichipor.taxiservice.database.DAO;

import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
    private static final String SQL_UPDATE_USER = "UPDATE users SET username = ?, password = ? WHERE userId = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE username = ? AND userId = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User tempUser;
        try(ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery(SQL_SELECT_ALL_USERS)){
            while (resultSet.next()) {
                tempUser = new User(resultSet.getInt("userId"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getBoolean("enabled"));
                users.add(tempUser);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    @Override
    public Map<String,List<Role>> getAllRoles() {
        Map<String,List<Role>> roles = new HashMap<>();
        try(ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery(SQL_SELECT_ALL_USER_ROLES)){
            while (resultSet.next()) {
                if (roles.containsKey(resultSet.getString("username"))) {
                    roles.get(resultSet.getString("username")).add(Role.valueOf(resultSet.getString("role")));
                } else {
                    roles.put(resultSet.getString("username"), new ArrayList<>());
                    roles.get(resultSet.getString("username")).add(Role.valueOf(resultSet.getString("role")));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return roles;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_SELECT_USER_BY_ID)){
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt("userId"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("enabled"));
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Closing resultSet exception " + e);
                }
            }
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_SELECT_USER_BY_USERNAME)){
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getInt("userId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("enabled"));
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Closing resultSet exception " + e);
                }
            }
        }
        return user;
    }

    @Override
    public List<Role> getUserRoles(User user) {
        List<Role> roles = new ArrayList<>();
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_SELECT_USER_ROLES)){
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                roles.add(Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Closing resultSet exception " + e);
                }
            }
        }
        return roles;
    }

    private boolean updateUserRole(User user, Role role, String sqlQuery) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, role.name());
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean addUserRole(User user, Role role) {
        return updateUserRole(user, role, SQL_INSERT_USER_ROLE);
    }

    @Override
    public boolean deleteUserRole(User user, Role role) {
        return updateUserRole(user, role, SQL_DELETE_USER_ROLE);
    }

    @Override
    public boolean deleteAllUserRoles(User user) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_DELETE_ALL_USER_ROLES)) {
            preparedStatement.setString(1, user.getUsername());
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean createUser(User user) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, user.getUserId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getUserId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }
}
