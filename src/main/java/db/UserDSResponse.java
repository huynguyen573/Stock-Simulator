package db;

import java.sql.Date;
import java.util.List;

/**
 * Response model class containing the information for creating a new user
 * object in the application from the entity database
 */
public class UserDSResponse {
    private final String username;
    private final String password;
    private final Date lastLogin;
    private final String compPort;
    private final List<PortfolioDSResponse> portfolios;

    public UserDSResponse(String username, String password, Date lastLogin, String compPort, List<PortfolioDSResponse> portfolios) {
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
        this.compPort = compPort;
        this.portfolios = portfolios;
    }


    /**
     * @return the username
     * Getter for the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return the password
     * Getter for the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return the lastLogin
     * Getter for the lastLogin
     */
    public Date getLastLogin() {
        return this.lastLogin;
    }

    /**
     * @return the compPort
     * Getter for the compPort
     */
    public String getCompPort() {
        return compPort;
    }

    /**
     * @return the portfolios
     * Getter for the portfolios
     */
    public List<PortfolioDSResponse> getPortfolios() {
        return this.portfolios;
    }
}