package APIInterface;

import java.io.IOException;

public interface StockDatabaseGateway {
    /**
     * Interface for the gateway to access stock prices
     */
    StockAPIResponse getPrice(StockAPIRequest req) throws IOException;

    StockAPIResponse getPriceHist(StockAPIRequest req) throws IOException;
}