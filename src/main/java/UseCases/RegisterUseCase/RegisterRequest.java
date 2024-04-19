package UseCases.RegisterUseCase;

import java.sql.Date;

/**
 * Request model object for when user attempts to register, contains
 * given username, password, password confirmation, and current date
 */
public final class RegisterRequest {
    private final String username;
    private final String password;
    private final String passwordConfirm;
    private final Date loginDate;


    /**
     * A request model class, carries input from the user to be processed by the interactor.
     * @param username the username of the user
     * @param password the password of the user
     * @param passwordConfirm the password confirmation of the user
     * @param loginDate the date of the user's login
     */
    public RegisterRequest(String username, String password, String passwordConfirm, Date loginDate) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.loginDate = loginDate;
    }

    /**
     * A getter for the username
     * @return the username
     */
    public String username() {
        return username;
    }

    /**
     * A getter for the password
     * @return the password
     */
    public String password() {
        return password;
    }

    /**
     * A getter for the passwordConfirm
     * @return the passwordConfirm
     */
    public String passwordConfirm() {
        return passwordConfirm;
    }

    /**
     * A getter for the loginDate
     * @return the loginDate
     */
    public Date loginDate() {
        return loginDate;
    }
}