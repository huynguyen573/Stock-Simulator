package APIInterface;

import yahoofinance.histquotes.HistoricalQuote;
import java.util.List;

public class StockAPIResponse {
    /**
     * Response model class containing output from an individual price request to the
     * stock price API.
     * Historical data is provided as a List of HistoricalQuote objects from the API.
     * For more information on the HistoricalQuote class and its attributes,
     * see <a href="https://financequotes-api.com/javadoc/yahoofinance/histquotes/HistoricalQuote.html">its documentation.</a>.
     */
    private final double price;
    private final List<HistoricalQuote> histData;
    
    public StockAPIResponse(double price, List<HistoricalQuote> histData){
        this.price = price;
        this.histData = histData;
    }

    /**
     * @return the price
     * Getter for the price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @return the histData
     * Getter for the histData
     */
    public List<HistoricalQuote> getHistData() {
        return histData;
    }
}