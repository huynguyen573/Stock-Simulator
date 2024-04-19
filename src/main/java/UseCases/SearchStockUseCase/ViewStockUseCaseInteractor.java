package UseCases.SearchStockUseCase;
import APIInterface.StockAPIGateway;
import APIInterface.StockAPIRequest;
import APIInterface.StockAPIResponse;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ViewStockUseCaseInteractor{
    /**
     * This is the interactor for the view stock use case, it is used to interact with the API and display the stock data to the user
     */
    private StockAPIResponse stock;
    private final String stockSymbol;
    private Calendar from = Calendar.getInstance();
    private final Interval stockPriceInterval = Interval.DAILY;

    public ViewStockUseCaseInteractor(String symbol){
        this.stockSymbol = symbol;
    }

    /*
    * Uses symbol declared on instantiation and throws error if symbol is invalid
    * */
    public void isValidStock() throws Exception {
        //Checking is API throws error or not
        try{
            new StockAPIGateway().getPrice(new StockAPIRequest(this.stockSymbol));
        }catch (IOException e){
            throw new Exception("Invalid stock symbol");
        }
    }
    /*
     * Uses symbol declared on instantiation and trys to search for the stock
     * */
    public void searchStock() throws IOException {
        this.from.add(Calendar.DATE, -7); //Date of the last 7 days
        this.stock = new StockAPIGateway().getPriceHist(new StockAPIRequest(this.stockSymbol, this.from, this.stockPriceInterval));
    }
    /**
     * @return List<HistoricalQuote> containing historical of last 7 days
     */
    public List<HistoricalQuote> getHistData(){
        return this.stock.getHistData();
    }

    /**
     * @param histDataRange specifies what interval the historical data should range from
     * @return String[][] contain date, and stock price on specific range
     */

    public String[][] sortHistoricalData(Interval histDataRange){
        this.from = Calendar.getInstance();
        if (histDataRange == Interval.DAILY){
            this.from.add(Calendar.DATE, -7);
        }else if (histDataRange == Interval.WEEKLY){
            this.from.setFirstDayOfWeek(Calendar.MONDAY);
            this.from.add(Calendar.MONTH, -2);
        }else{
            this.from.add(Calendar.YEAR, -7);
        }

        try{
            this.stock = new StockAPIGateway().getPriceHist(new StockAPIRequest(this.stockSymbol, this.from, histDataRange));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<HistoricalQuote> histData = stock.getHistData();
        //Setting up the JTable
        String[][] data = new String[histData.size()][2];
        int row = 0;
        for (HistoricalQuote q : histData) {
            data[row][0] = new SimpleDateFormat("dd/MM/yyyy").format(q.getDate().getTime());
            data[row][1] = new DecimalFormat("0.00").format(q.getClose());
            row++;
        }
        return data;
    }
    /**
     * Returns current stock price
     * @return double representing current price of stock
     */
    public double getStockValue() throws IOException {
        this.stock = new StockAPIGateway().getPriceHist(new StockAPIRequest(this.stockSymbol, this.from, this.stockPriceInterval));
        return this.stock.getPrice();
    }
}