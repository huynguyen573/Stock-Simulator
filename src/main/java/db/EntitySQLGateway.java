package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL implementation of the entity database gateway interface,
 * contains methods such as adding, deleting, and updating user, portfolio,
 * and stock objects in the database
 */
public class EntitySQLGateway implements EntityDBGateway {
    Connection con;

    /**
     * Initializes a connection to the SQL database
     */
    public EntitySQLGateway() {
        String dbURL = "jdbc:mysql://db-mysql-nyc1-71885-do-user-10038162-0.b.db.ondigitalocean.com:25060/defaultdb";
        String user = "doadmin";
        String pass = "AVNS_3ACCOAF3QXEZedJQXcx";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, user, pass);
        } catch (
                SQLException e) {
            e.printStackTrace();
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param username non-empty string representing a username
     * @param compPort non-empty string representing the user's competitive
     *                 portfolio's name
     *                 sets given portfolio name as the user's new competitive portfolio
     */
    @Override
    public void addCompPort(String username, String compPort) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE Users SET " +
                    "competitivePort = '" + compPort + "' WHERE " +
                    "username = '" + username + "'");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param username non-empty string representing a username
     *                 deletes the given user from the database
     */
    @Override
    public void deleteUser(String username) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT name FROM Portfolios WHERE " +
                    "username = ?");
            ps.setString(1, username);
            ResultSet portRS = ps.executeQuery();

            while (portRS.next()) {
                deletePortfolio(
                        portRS.getString(1),
                        username);
            }

            Statement st = con.createStatement();
            st.execute(
                    "DELETE FROM Users WHERE " +
                            "username = '" + username + "'");

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return list of user database response objects representing all the users
     * existing in the database
     */
    @Override
    public List<UserDSResponse> getAllUsers() {
        try {
            List<UserDSResponse> userDSResponses = new ArrayList<>();

            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Users");
            ResultSet userRS = st.executeQuery();
            while (userRS.next()) {
                userDSResponses.add(getUser(
                        userRS.getString(1),
                        userRS)
                );
            }
            return userDSResponses;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param newUser user ds request model containing information for adding a new
     *                user to the database
     *                adds a new user to the database
     */
    @Override
    public void addUser(UserDSRequest newUser) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO Users VALUES ('" +
                    newUser.getUsername() + "','" +
                    newUser.getPassword() + "','" +
                    newUser.getLastLogin() + "','" +
                    null + "')");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <pre>
     * Searches for user in database based on given username
     *
     * Returns User object if it is found, null otherwise
     * </pre>
     */
    @Override
    public boolean findUser(String username) {
        try {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Users WHERE username = ?");
            st.setString(1, username);

            return st.executeQuery().isBeforeFirst();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDSResponse getUser(String username, ResultSet userRS) {
        try {
            List<PortfolioDSResponse> portfolioDSResponses = new ArrayList<>();

            PreparedStatement st = con.prepareStatement(
                    "SELECT name FROM Portfolios WHERE username = ?");
            st.setString(1, username);
            ResultSet portfolioRS = st.executeQuery();

            while (portfolioRS.next()) {
                portfolioDSResponses.add(findPortfolio(
                        portfolioRS.getString(1),
                        username
                ));
            }

            return new UserDSResponse(userRS.getString(1),
                    userRS.getString(2),
                    userRS.getDate(3),
                    userRS.getString(4),
                    portfolioDSResponses);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param username a non-empty string following the valid username parameters
     * @param password a non-empty string following the valid username parameters
     * @return user database response model containing a user's info and only
     * their portfolio names instead of their full information to make calls more efficient
     */
    @Override
    public UserDSResponse findUserPortfolios(String username, String password) {
        try {
            List<PortfolioDSResponse> portfolioDSResponses = new ArrayList<>();

            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Users WHERE " +
                            "username = ? AND " +
                            "password = ?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet userRS = st.executeQuery();
            boolean userFound = userRS.next();

            st = con.prepareStatement(
                    "SELECT name, balance FROM Portfolios WHERE username = ?");
            st.setString(1, username);
            ResultSet portfolioRS = st.executeQuery();

            while (portfolioRS.next()) {
                portfolioDSResponses.add(new PortfolioDSResponse(
                        portfolioRS.getString(1),
                        portfolioRS.getDouble(2),
                        new ArrayList<>()
                ));
            }
            if (userFound) {
                return new UserDSResponse(userRS.getString(1),
                        userRS.getString(2),
                        userRS.getDate(3),
                        userRS.getString(4),
                        portfolioDSResponses);
            }

            return null;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param username  username a non-empty string following the valid username parameters
     * @param loginDate date of when user logged in
     *                  Updates the user's login date in the database
     */
    @Override
    public void updateUserLoginDate(String username, Date loginDate) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "UPDATE Users SET " +
                            "lastLogin = '" + loginDate + "' WHERE " +
                            "username = '" + username + "'");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds portfolio to given user in the database
     *
     * @param newPortfolio the new portfolio to be added to the database
     */
    @Override
    public void addPortfolio(PortfolioDSRequest newPortfolio) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO Portfolios VALUES ('" +
                    newPortfolio.getName() + "','" +
                    newPortfolio.getBalance() + "','" +
                    newPortfolio.getUsername() + "')");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param portfolioName a non-empty string following the valid portfolio name parameters
     * @param username      a non-empty string following the valid username parameters
     *                      <pre>
     *                                           Searches for portfolio in database based on given username and portfolioName
     *
     *                                           @return User object if it is found, null otherwise
     *                                           </pre>
     */
    @Override
    public PortfolioDSResponse findPortfolio(String portfolioName, String username) {
        try {
            List<StockDSResponse> stockDSResponses = new ArrayList<>();

            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Portfolios WHERE " +
                            "name = ? AND " +
                            "username = ?");
            st.setString(1, portfolioName);
            st.setString(2, username);
            ResultSet portfolioRS = st.executeQuery();
            boolean portfolioFound = portfolioRS.next();

            st = con.prepareStatement("SELECT stockName FROM PortfolioStock WHERE " +
                    "portfolioName = ? AND " +
                    "username = ?");
            st.setString(1, portfolioName);
            st.setString(2, username);
            ResultSet portfolioStockRS = st.executeQuery();

            while (portfolioStockRS.next()) {
                stockDSResponses.add(findStock(
                        portfolioStockRS.getString(1),
                        username,
                        portfolioName));
            }

            if (portfolioFound) {
                return new PortfolioDSResponse(
                        portfolioRS.getString(1),
                        portfolioRS.getDouble(2),
                        stockDSResponses);
            }

            return null;

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param name     a non-empty string following the valid portfolio name parameters
     * @param username a non-empty string following the valid username parameters
     *                 deletes the given portfolio from the database if it exists
     */
    @Override
    public void deletePortfolio(String name, String username) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT stockName FROM PortfolioStock WHERE " +
                    "portfolioName = ? AND " +
                    "username = ?");
            ps.setString(1, name);
            ps.setString(2, username);
            ResultSet stockRS = ps.executeQuery();

            while (stockRS.next()) {
                deleteStock(
                        stockRS.getString(1),
                        username,
                        name);
            }

            Statement st = con.createStatement();
            st.execute(
                    "DELETE FROM Portfolios WHERE " +
                            "name = '" + name + "' AND " +
                            "username = '" + username + "'");

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param name       a non-empty string following the valid portfolio name parameters
     * @param newBalance a positive amount representing the portfolios updated balance
     * @param username   a non-empty string following the valid username parameters
     */
    @Override
    public void updatePortfolioBalance(String name, double newBalance, String username) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "UPDATE Portfolios SET " +
                            "balance = '" + newBalance + "' WHERE " +
                            "name = '" + name + "' AND " +
                            "username = '" + username + "'");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param newStock stock ds request model containing information for adding a new
     *                 stock to the database
     *                 adds a new stock to the database
     */
    @Override
    public void addStock(StockDSRequest newStock) {
        try {
            Statement st;

            if (!findStock(newStock.getSymbol())) {
                st = con.createStatement();
                st.execute(
                        "INSERT INTO Stocks VALUES ('" +
                                newStock.getSymbol() + "','" +
                                newStock.getValue() + "')");
            }

            st = con.createStatement();
            st.executeUpdate(
                    "INSERT INTO PortfolioStock VALUES ('" +
                            newStock.getPortfolioName() + "','" +
                            newStock.getSymbol() + "','" +
                            newStock.getQuantity() + "','" +
                            newStock.getUsername() + "')");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param symbol        a non-empty string representing a stock name
     * @param username      a non-empty string following the valid username parameters
     * @param portfolioName a non-empty string following the valid portfolio name parameters
     * @return stock database response model containing a stock's info belonging to the
     * specified portfolio
     */
    @Override
    public StockDSResponse findStock(String symbol, String username, String portfolioName) {
        try {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Stocks WHERE symbol = ?");
            st.setString(1, symbol);
            ResultSet stockRS = st.executeQuery();
            stockRS.next();

            st = con.prepareStatement(
                    "SELECT quantity FROM PortfolioStock WHERE " +
                            "portfolioName = ? AND " +
                            "stockName = ? AND " +
                            "username = ?");
            st.setString(1, portfolioName);
            st.setString(2, symbol);
            st.setString(3, username);
            ResultSet portfolioStockRS = st.executeQuery();

            if (portfolioStockRS.next()) {
                return new StockDSResponse(
                        stockRS.getString(1),
                        stockRS.getDouble(2),
                        portfolioStockRS.getInt(1));
            }

            return null;


        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param symbol a non-empty string representing a stock name
     * @return weather a stock with the given symbol nae exists in the database
     */
    @Override
    public boolean findStock(String symbol) {
        try {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Stocks WHERE symbol = ?");
            st.setString(1, symbol);

            return st.executeQuery().isBeforeFirst();

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param symbol        a non-empty string representing a stock name
     * @param username      a non-empty string following the valid username parameters
     *                      deletes the given portfolio from the database if it exists
     * @param portfolioName a non-empty string following the valid portfolio name parameters
     */
    @Override
    public void deleteStock(String symbol, String username, String portfolioName) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "DELETE FROM PortfolioStock WHERE " +
                            "portfolioName = '" + portfolioName + "' AND " +
                            "stockName = '" + symbol + "' AND " +
                            "username = '" + username + "'");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PortfolioStock WHERE " +
                    "stockName = ?");
            ps.setString(1, symbol);

            if (!ps.executeQuery().isBeforeFirst()) {
                st = con.createStatement();
                st.executeUpdate("DELETE FROM Stocks WHERE " +
                        "symbol = '" + symbol + "'");
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param symbol        a non-empty string representing a stock name
     * @param newQuantity   positive value representing the stock's updated quantity
     *                      the given portfolio owns of it
     * @param username      a non-empty string following the valid username parameters
     *                      deletes the given portfolio from the database if it exists
     * @param portfolioName a non-empty string following the valid
     *                      portfolio name parameters
     */
    @Override
    public void updateStockQuantity(String symbol, int newQuantity, String username, String portfolioName) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "UPDATE PortfolioStock SET " +
                            "quantity = '" + newQuantity + "' WHERE " +
                            "portfolioName = '" + portfolioName + "' AND " +
                            "stockName = '" + symbol + "' AND " +
                            "username = '" + username + "'");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}