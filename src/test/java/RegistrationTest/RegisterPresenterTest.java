package RegistrationTest;

import Controllers.RegisterController;
import UseCases.RegisterUseCase.*;
import db.UserDSRequest;
import db.EntityDBGateway;
import main.UserManager;
import main.OuterLayerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;


class RegisterPresenterTest {

    private static RegisterController controller;
    private static EntityDBGateway dbGateway;
    private static final String correctUsername = "RegisterUserTest";
    private static final String correctPassword = "password";
    private static final String correctPasswordConfirm = "password";

    @BeforeAll
    public static void setUp() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        registerInteractor.signUpUser(correctUsername, correctPassword, correctPasswordConfirm, Date.valueOf(LocalDate.now()));
        controller = new RegisterController();
        UserManager userManager = UserManager.instance;
        dbGateway = OuterLayerFactory.instance.getEntityDSGateway();

        if (!userManager.userExists(correctUsername)) {
            dbGateway.addUser(new UserDSRequest(correctUsername, correctPassword, Date.valueOf(LocalDate.now())));
        }
    }
    private static RegisterResponse getRegisterResponse(String correctUsername, String password, String passwordConfirm) {
        RegisterRequest request = new RegisterRequest(
                correctUsername,
                password,
                passwordConfirm,
                Date.valueOf(LocalDate.now()));

        return controller.signUpUser(request);
    }

    /**
     * Inputs a correct combination with no issues to return RegisterError.NONE
     */
    @Test
    public void testNoIssue() {
        // make a random name
        dbGateway.deleteUser(correctUsername);

        RegisterResponse response = getRegisterResponse(correctUsername, "password", "password");
        Assertions.assertEquals(RegisterError.NONE, response.userSignedUp());

    }

    /**
     * Inputs invalid passwords to receive RegisterError.PASSWORD_INVALID
     */
    @Test
    public void testShortPassword() {
        RegisterResponse response = getRegisterResponse("userDoesn'tExist", "h1", "h1");
        Assertions.assertEquals(RegisterError.PASSWORD_INVALID, response.userSignedUp());
    }


    /**
     * Inputs a username that is already taken
     */
    @Test
    public void testUserExistsError() {
        RegisterResponse response = getRegisterResponse(correctUsername, correctPassword, correctPassword);
        Assertions.assertEquals(RegisterError.USERNAME, response.userSignedUp());

    }

    /**
     * Inputs different passwords to receive RegisterError.PASSWORD_NOT_MATCH
     */
    @Test
    public void testDifferentPasswordsError() {
        dbGateway.deleteUser(correctUsername);
        RegisterResponse response = getRegisterResponse(correctUsername, "PasswordNotSame", "NotRight");
        Assertions.assertEquals(RegisterError.PASSWORD_NOT_MATCH, response.userSignedUp());

    }


    /**
     * Space in Username
     */
    @Test
    public void testSpacedUsername() {
        RegisterResponse response = getRegisterResponse("Random Name", "PasswordNotSame", "PasswordNotSame");
        Assertions.assertEquals(RegisterError.USERNAME, response.userSignedUp());

    }
    /**
     * Testing Long Username
     */
    @Test
    public void testLongUsername() {
        // Building a long username that is 51 characters long
        RegisterResponse response = getRegisterResponse("a" + "a".repeat(50), "PasswordNotSame", "PasswordNotSame");
        Assertions.assertEquals(RegisterError.USERNAME, response.userSignedUp());

    }

    /**
     * Testing Long Username
     */
    @Test
    public void testLongPassword() {
        // Building a long username that is 51 characters long
        StringBuilder longPassword = new StringBuilder("a");
        longPassword.append("a".repeat(50));
        dbGateway.deleteUser(correctUsername);
        RegisterResponse response = getRegisterResponse(correctUsername, longPassword.toString(), longPassword.toString());
        Assertions.assertEquals(RegisterError.PASSWORD_INVALID, response.userSignedUp());
    }

    /**
     * Testing With " in Username
     */
    @Test
    public void testQuotation() {
        String username = "username\"";
        RegisterResponse response = getRegisterResponse(username, correctPassword, correctPasswordConfirm);
        Assertions.assertEquals(response.userSignedUp(), RegisterError.USERNAME);

    }

}