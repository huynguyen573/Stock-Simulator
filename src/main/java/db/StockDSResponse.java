package db;

/**
 * Response model class containing the information for creating a new stock
 * object in the application from the entity database
 */
public class StockDSResponse {
    private final String symbol;
    private final double value;
    private final int quantity;

    public StockDSResponse (String symbol, double value, int quantity){
        this.symbol = symbol;
        this.value = value;
        this.quantity = quantity;
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
    public double getValue() {
        return this.value;
    }

    /**
     * @return int quantity of the stock
     * Getter for the quantity of the stock
     */
    public int getQuantity(){
        return this.quantity;
    }
}