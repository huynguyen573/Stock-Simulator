package UseCases.RegisterUseCase;

import main.UserManager;

import java.sql.Date;

public class RegisterInteractor {
    private final UserManager userManager;

    /**
     * A constructor for the RegisterInteractor class
     */
    public RegisterInteractor() {
        userManager = UserManager.instance;
    }

    /**
     * A method that generates a response based on the request
     * @return RegisterError type such that it can be a Username or Password error or no error
     */

    public RegisterError signUpUser(String username, String password, String passwordConfirm, Date loginDate) {

        // Refactored by adding methods to check for errors in order to make the code more readable
        if (usernameInvalid(username)) {
            return RegisterError.USERNAME;
        } else if (!passwordValid(password)) {
            return RegisterError.PASSWORD_INVALID;
        }else if (!passwordMatch(password, passwordConfirm)) {
            return RegisterError.PASSWORD_NOT_MATCH;
        }
        else {
            userManager.createUser(username, password, loginDate);
            return RegisterError.NONE;
        }
    }


    /**
     * @return True if the username is invalid
     * Refactored
     */
    private boolean usernameInvalid(String username) {
        // case 1: username is already taken, case 2: username is empty, case 3: username contains spaces
        // case 4: username is too long (SQL Error) and quotation mark
        return userManager.userExists(username) || username.equals("") || username.contains(" ") || username.length() > 50 || username.contains("\"");
    }

    /**
     * @return True if password length is in range [8, 50] otherwise false
     */
    private boolean passwordValid(String password) {
        int minLength = 8;
        int maxLength = 50;
        // SQL Column Error for length > 50

        if (password.contains("\"")) {
            // SQL Error for quotation mark
            return false;
        }

        return password.length() >= minLength && password.length() <= maxLength;
    }

    /**
     * @param password the password of the user
     * @param passwordConfirm the password confirmation of the user
     * @return True if password and passwordConfirm match, else false
     */
    private boolean passwordMatch(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
}