package UserLoginPresenterTest;

import Controllers.UserLoginController;
import UseCases.LoginUseCase.UserLoginRequest;
import UseCases.LoginUseCase.UserLoginResponse;
import UseCases.RegisterUseCase.RegisterInteractor;
import entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

public class UserLoginPresenterTest {
    private static UserLoginController controller;
    private static final String correctUsername = "LoginTestUser";
    private static final String correctPassword = "password";

    @BeforeAll
    public static void setUp() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        registerInteractor.signUpUser(correctUsername, correctPassword, correctPassword, Date.valueOf(LocalDate.now()));
        controller = new UserLoginController();
    }

    /**
     * Test that the presenter correctly handles a failed login with an incorrect username
     */
    @Test
    public void testLoginFalseUsername() {
        UserLoginRequest request = new UserLoginRequest(
                "userDoesn'tExist",
                "password",
                Date.valueOf(LocalDate.now()));

        UserLoginResponse response = controller.loginUser(request);
        Assertions.assertNull(response.user());
    }


    /**
     * Test an invalid password to ensure that the user cannot login
     */
    @Test
    public void testLoginFalsePassword() {
        UserLoginRequest request = new UserLoginRequest(
                "username",
                "wrongPassword",
                Date.valueOf(LocalDate.now()));

        UserLoginResponse response = controller.loginUser(request);
        Assertions.assertNull(response.user());
    }

    /**
     * Test that the user is logged in when the correct username and password are entered
     */
    @Test
    public void testLoginUserExists() {
        Date loginTime = Date.valueOf(LocalDate.now());
        UserLoginRequest request = new UserLoginRequest(
                correctUsername,
                correctPassword,
                loginTime);

        UserLoginResponse response = controller.loginUser(request);
        User user = response.user();
        Assertions.assertEquals(user.getUsername(), correctUsername);
        Assertions.assertTrue(user.isPassword(correctPassword));
    }
}