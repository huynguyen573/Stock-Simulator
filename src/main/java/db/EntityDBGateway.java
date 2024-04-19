package db;

import java.sql.Date;
import java.util.List;

/**
 * An interface containing all necessary methods to interact with an implementation
 * of an entity database, with methods such as adding, deleting, and modifying
 * user, portfolio, and stock objects in the database
 */
public interface EntityDBGateway {

    /**
     * @param username non-empty string representing a username
     * @param compPort non-empty string representing the user's competitive
     *                 portfolio's name
     * sets given portfolio name as the user's new competitive portfolio
     */
    void addCompPort(String username, String compPort);

    /**
     * @param username non-empty string representing a username
     * deletes the given user from the database
     */
    void deleteUser(String username);

    /**
     * @return list of user database response objects representing all the users
     * existing in the database
     */
    List<UserDSResponse> getAllUsers();

    /**
     * @param newUser user ds request model containing information for adding a new
     *                user to the database
     * adds a new user to the database
     */
    void addUser(UserDSRequest newUser);

    /**
     * <pre>
     * Searches for user in database based on given username
     *
     * Returns User object if it is found, null otherwise
     * </pre>
     */
    boolean findUser(String username);

    /**
     * @param username a non-empty string following the valid username parameters
     * @param password a non-empty string following the valid username parameters
     * @return user database response model containing a user's info and only
     * their portfolio names instead of their full information to make calls more efficient
     */
    UserDSResponse findUserPortfolios(String username, String password);

    /**
     * @param username username a non-empty string following the valid username parameters
     * @param loginDate date of when user logged in
     * Updates the user's login date in the database
     */
    void updateUserLoginDate(String username, Date loginDate);

    /**
     * Adds portfolio to given user in the database
     */
    void addPortfolio(PortfolioDSRequest newPortfolio);

    /**
     *  @param portfolioName a non-empty string following the valid portfolio name parameters
     *  @param username a non-empty string following the valid username parameters
     * <pre>
     * Searches for portfolio in database based on given username and portfolioName
     *
     * @return User object if it is found, null otherwise
     * </pre>
     */
    PortfolioDSResponse findPortfolio(String portfolioName, String username);

    /**
     * @param name a non-empty string following the valid portfolio name parameters
     * @param username a non-empty string following the valid username parameters
     * deletes the given portfolio from the database if it exists
     */
    void deletePortfolio(String name, String username);

    /**
     * @param name a non-empty string following the valid portfolio name parameters
     * @param newBalance a positive amount representing the portfolios updated balance
     * @param username a non-empty string following the valid username parameters
     */
    void updatePortfolioBalance(String name, double newBalance, String username);

    /**
     * @param newStock stock ds request model containing information for adding a new
     *                stock to the database
     * adds a new stock to the database
     */
    void addStock(StockDSRequest newStock);

    /**
     * @param symbol a non-empty string representing a stock name
     * @param username a non-empty string following the valid username parameters
     * @param portfolioName a non-empty string following the valid portfolio name parameters
     * @return stock database response model containing a stock's info belonging to the
     * specified portfolio
     */
    StockDSResponse findStock(String symbol, String username, String portfolioName);


    /**
     * @param symbol a non-empty string representing a stock name
     * @return weather a stock with the given symbol nae exists in the database
     */
    boolean findStock(String symbol);

    /**
     * @param symbol a non-empty string representing a stock name
     * @param portfolioName a non-empty string following the valid portfolio name parameters
     * @param username a non-empty string following the valid username parameters
     * deletes the given portfolio from the database if it exists
     */
    void deleteStock(String symbol, String username, String portfolioName);

    /**
     * @param symbol a non-empty string representing a stock name
     * @param newQuantity positive value representing the stock's updated quantity
     *                    the given portfolio owns of it
     * @param portfolioName a non-empty string following the valid
     *                      portfolio name parameters
     * @param username a non-empty string following the valid username parameters
     * deletes the given portfolio from the database if it exists
     */
    void updateStockQuantity(String symbol, int newQuantity, String username, String portfolioName);
}