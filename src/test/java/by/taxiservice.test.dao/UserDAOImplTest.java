package by.taxiservice.test.dao;

import by.nichipor.taxiservice.database.dao.UserDAO;
import by.nichipor.taxiservice.entity.Role;
import by.nichipor.taxiservice.entity.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:appServlet-servlet-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {
    private static int numberOfUsers = 3;
    private static int numberOfUsersRoles = 1;
    private static int numberOfRoles = 3;
    private User user;
    private User testUser;

    @Autowired
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        user = new User("user", "user");
        testUser = new User("test", "test");
    }

    @Test
    public void findAllUsersTest() throws Exception {
        int expected = numberOfUsers;
        int actual = userDAO.findAllUsers().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void findAllRolesTest() throws Exception {
        int expected = numberOfRoles;
        int actual = userDAO.findAllRoles().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void findUserByIdTest() throws Exception {
        int id = 2;
        String expected = user.getUsername();
        String actual = userDAO.findUserById(id).getUsername();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findUserByUsernameTest() throws Exception {
        String expected = user.getUsername();
        String actual = userDAO.findUserByUsername(expected).getUsername();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addUserRoleTest() throws Exception {
        userDAO.addUserRole(user, Role.ROLE_ADMIN);
        int expected = ++numberOfUsersRoles;
        int actual = userDAO.findUserRoles(user).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void findUserRolesTest() throws Exception {
        int expected = numberOfUsersRoles;
        int actual = userDAO.findUserRoles(user).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void deleteUserRoleTest() throws Exception {
        int expected = 0;
        if (userDAO.deleteUserRole(user, Role.ROLE_USER)) {
            expected = --numberOfUsersRoles;
        }
        int actual = userDAO.findUserRoles(user).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void deleteAllUserRolesTest() throws Exception {
        User tempUser = new User("admin", "afsdf");
        userDAO.deleteAllUserRoles(tempUser);
        int expected = 0;
        int actual = userDAO.findUserRoles(tempUser).size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void createUserTest() throws Exception {
        userDAO.createUser(testUser);
        int expected = ++numberOfUsers;
        int actual = userDAO.findAllUsers().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void deleteUserTest() throws Exception {
        User tempUser = userDAO.findUserByUsername("dispatcher");
        userDAO.deleteUser(tempUser);
        int expected = --numberOfUsers;
        int actual = userDAO.findAllUsers().size();
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void updateUserTest() throws Exception {
        User tempUser = new User("notUpdated", "dsad");
        userDAO.createUser(tempUser);
        tempUser = userDAO.findUserByUsername("notUpdated");
        ++numberOfUsers;
        String expected = "updated";
        tempUser.setUsername(expected);
        userDAO.updateUser(tempUser);
        String actual = userDAO.findUserByUsername(expected).getUsername();
        Assert.assertEquals(expected, actual);
    }

}