package main;

import db.UserDSRequest;
import db.UserDSResponse;
import db.EntityDBGateway;
import entities.User;
import entities.UserFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    /**
     * This class is responsible for managing the creation and deletion of User objects
     */
    public static final UserManager instance =
            new UserManager(
                    OuterLayerFactory.instance.getEntityDSGateway());
    private User user;
    private final EntityDBGateway dbGateway;
    private final UserFactory userFactory = new UserFactory();

    public UserManager(EntityDBGateway dbGateway) {
        this.dbGateway = dbGateway;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        for (UserDSResponse userDSResponse : dbGateway.getAllUsers()) {
            users.add(convertUserDSResponse(userDSResponse));
        }

        return users;
    }

    /**
     * @param username non-empty string
     * @param password non-empty string
     * @return the user from the system that has the given username password pair
     * or null if it doesn't exist
     */
    public User getUser(String username, String password, Date loginDate) {
        User user = convertUserDSResponse(dbGateway.findUserPortfolios(username, password));

        if (user != null) {
            this.user = user;
            dbGateway.updateUserLoginDate(username, loginDate);
        }

        return user;
    }

    /**
     * @param username non-empty string
     * @return true if a user in the system has a matching username, false otherwise
     */
    public boolean userExists(String username) {
        if (user != null && user.getUsername().equals(username)) {
            return true;
        }

        return dbGateway.findUser(username);
    }

    /**
     * <p>
     * Creates new user that doesn't exist in the system already
     * and adds it to the database
     * </p>
     *
     * @param username    unique username that does not exist in the system already
     * @param password    non-empty string satisfying valid password parameters
     * @param dateCreated String describing a date
     */
    public void createUser(String username, String password, Date dateCreated) {
        dbGateway.addUser(new UserDSRequest(username, password, dateCreated));
    }

    /**
     * @param userDSResponse response from the database
     * @return a user object from the response
     */
    private User convertUserDSResponse(UserDSResponse userDSResponse) {
        if (userDSResponse == null) {
            return null;
        }

        return userFactory.createUser(
                userDSResponse.getUsername(),
                userDSResponse.getPassword(),
                userDSResponse.getLastLogin(),
                userDSResponse.getCompPort(),
                userDSResponse.getPortfolios());
    }
    
    /**
     * Getter for user
     * @return the user that is currently logged in
     */
    public User getUser() {
        return user;
    }
}