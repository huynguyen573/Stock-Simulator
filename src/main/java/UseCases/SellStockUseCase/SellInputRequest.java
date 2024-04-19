package UseCases.SellStockUseCase;
import entities.Portfolio;

public class SellInputRequest {
    /**
     * This class is used to take user's requests for the sell stock use case
     */
    private final Portfolio portfolio;
    private final String symbol;
    private final int quantity;

    /**
     * This constructor is used to create a request for the sell stock use case
     * @param portfolio the portfolio to sell from
     * @param symbol the symbol of the stock to sell
     * @param quantity the quantity of the stock to sell
     */
    public SellInputRequest(Portfolio portfolio, String symbol, int quantity) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.quantity = quantity;
    }
    public Portfolio getPortfolio() {
        return portfolio;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getQuantity() {
        return quantity;
    }
}