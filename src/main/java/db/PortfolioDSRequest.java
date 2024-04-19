package db;

/**
 * Request model class containing input for creating a new portfolio
 * object in the entity database
 */
public class PortfolioDSRequest {
    private final String name;
    private final double balance;
    private final String username;
    
    public PortfolioDSRequest(String name, double balance, String username){
        this.name = name;
        this.balance = balance;
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getUsername() {
        return this.username;
    }
}