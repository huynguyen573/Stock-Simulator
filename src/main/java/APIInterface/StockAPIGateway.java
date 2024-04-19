package APIInterface;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockAPIGateway implements StockDatabaseGateway {

    /**
     * @param req StockAPIRequest containing a String representing the symbol of the stock to be searched
     * @return StockAPIResponse containing the price of the stock and a list of historical price information for that stock
     * @throws IOException when there's a connection problem with the API
     */
    @Override
    public StockAPIResponse getPrice(StockAPIRequest req) throws IOException {
        Stock stock = YahooFinance.get(req.getSymbol());
        List<HistoricalQuote> histQuotes = new ArrayList<>();
        return new StockAPIResponse(stock.getQuote().getPrice().doubleValue(), histQuotes);
    }

    /**
     * @param req StockAPIRequest containing a String representing the symbol of the stock to be searched
     * @return StockAPIResponse containing the price of the stock and a list of historical price information for that stock
     * @throws IOException when there's a connection problem with the API
     */
    public StockAPIResponse getPriceHist(StockAPIRequest req) throws IOException {
        Stock stock = YahooFinance.get(req.getSymbol(), req.getFrom(), req.getPriceInterval());
        List<HistoricalQuote> histQuotes = stock.getHistory();
        return new StockAPIResponse(stock.getQuote().getPrice().doubleValue(), histQuotes);
    }
}