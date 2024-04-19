package db;

/**
 * Request model class containing input for creating a new stock
 * object in the entity database
 */
public class StockDSRequest {
    private final String symbol;
    private final double value;
    private final int quantity;
    private final String username;
    private final String portfolioName;

    public StockDSRequest(String symbol, double value, int quantity, String username, String portfolioName){
        this.symbol = symbol;
        this.value = value;
        this.quantity = quantity;
        this.username = username;
        this.portfolioName = portfolioName;
    }

    /**
     * @return String symbol of the stock
     * Getter for the symbol of the stock
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * @return double value of the stock
     * Getter for the value of the stock
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return int quantity of the stock
     * Getter for the quantity of the stock
     */
    public String getPortfolioName() {
        return this.portfolioName;
    }

    /**
     * @return String username of the stock
     * Getter for the username of the stock
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * @return String portfolioName of the stock
     * Getter for the portfolioName of the stock
     */
    public double getValue() {
        return this.value;
    }
}