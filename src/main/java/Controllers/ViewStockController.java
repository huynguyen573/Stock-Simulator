package Controllers;

import UseCases.SearchStockUseCase.ViewStockUseCaseInteractor;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;

public class ViewStockController {
    /**
     * This is the controller for the view stock use case
     */
    private final ViewStockUseCaseInteractor interactor;

    public ViewStockController(String symbol){
        this.interactor = new ViewStockUseCaseInteractor(symbol);
    }

    public void stockIsValid() throws Exception {
        interactor.isValidStock();
    }

    public void searchStock() throws IOException {
        interactor.searchStock();
    }

    public DefaultTableModel updateTable(Interval tableRange){
        // Column Names
        String[] columnNames = {"Date", "Stock Price"};
        String[][] data = interactor.sortHistoricalData(tableRange);
        return new DefaultTableModel(data, columnNames);
    }

    public List<HistoricalQuote> getCurrentHistData() {
        return interactor.getHistData();
    }

    public double getCurrentPrice() throws IOException {
        return interactor.getStockValue();
    }
}