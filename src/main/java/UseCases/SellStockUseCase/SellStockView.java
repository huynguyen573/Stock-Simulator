package UseCases.SellStockUseCase;

public interface SellStockView {
     /**
      * This is the interface for the sell stock use case
      * Refactored from SellStockUseCase.SellStockGUI
      */
     void close();
     void displayQuantityFailure();
     void displayConnectionFailure();
     int getQuantity();
     void addSellAction(Runnable onSell);
     void addGoBackAction(Runnable onBack);
     void displaySuccess();
     String getSymbol();
    void updateQuantityLabel(int quant);
}