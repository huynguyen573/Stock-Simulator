package UseCases.SearchStockUseCase;


import yahoofinance.histquotes.HistoricalQuote;

import javax.swing.table.DefaultTableModel;
import java.util.List;


public interface ViewStockView {
    /**
     * This is the interface for the view stock use case
     */
    void updateTable(DefaultTableModel tableModel);

    void refreshButtonAction(Runnable onRefreshButton);

    void todayButtonAction(Runnable onTodayButton);

    void weeklyButtonAction(Runnable onWeeklyButton);

    void yearlyButtonAction(Runnable onYearlyButton);

    /**
     * This is the interface for the SearchStock use case
     * Refactored from SearchStockUseCase.ViewStockGUI
     */
    void addBuyStockAction(Runnable onBuyStock);
    void addSellStockAction(Runnable onSellStock);
    void addBackAction(Runnable onBack);
    String stockMarketStatus();
    void close();
    String getStockSymbol();
    void setHistData(List<HistoricalQuote> historicalQuotes);
    void setStockPrice(double stockPrice);
    void loadLabels();
}