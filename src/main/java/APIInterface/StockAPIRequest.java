package APIInterface;

import yahoofinance.histquotes.Interval;

import java.util.Calendar;


public class StockAPIRequest {
    /**
     * Request model class containing input for an individual price request to the
     * stock price API.
     */
    private final String symbol;
    private Calendar from;
    private Interval priceInterval;
    
    public StockAPIRequest(String symbol){
        this.symbol = symbol;
    }

    public StockAPIRequest(String symbol, Calendar from, Interval interval){
        this.symbol = symbol;
        this.from = from;
        this.priceInterval = interval;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Calendar getFrom() {
        return from;
    }

    public Interval getPriceInterval() {
        return priceInterval;
    }
}