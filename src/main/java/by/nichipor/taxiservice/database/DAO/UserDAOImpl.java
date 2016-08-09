package by.nichipor.taxiservice.database.DAO;

import by.nichipor.taxiservice.entity.Roles;
import by.nichipor.taxiservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Nichipor on 09.08.2016.
 */

@Component
public class UserDAOImpl implements UserDAO {

    private final static String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private final static String SQL_SELECT_USER_ROLES = "SELECT * FROM user_roles WHERE userId = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        User tempUser = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                tempUser = new User(resultSet.getInt("userId"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getBoolean("enabled"));
                users.add(tempUser);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Closing statement exception " + e);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Closing resultSet exception " + e);
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public User findUserById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Roles> findUserRoles(User user) {
        List<Roles> roles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ROLES);
            preparedStatement.setInt(1, user.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                roles.add(Roles.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Closing prepared statement exception " + e);
                }
            }
        }
        return roles;
    }

    @Override
    public boolean addUserRole(User user, Roles role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteUserRole(User user, Roles role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteUser(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateUser(User user) {
        throw new UnsupportedOperationException();
    }
}
