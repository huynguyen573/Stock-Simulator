package UseCases.BuyStockUseCase;

import Controllers.BuyStockController;
import UseCases.SearchStockUseCase.ViewStockPresenter;
import entities.Portfolio;
import entities.User;

import main.OuterLayerFactory;

public class BuyStockPresenter {
    /**
     * Presenter for the buy stock GUI
     */
    private final BuyStockView view;
    private final Portfolio currentPort;
    private final User user;

    public BuyStockPresenter(BuyStockView view, Portfolio currentPort, User user) {
        this.view = view;
        this.user = user;
        view.addBuyAction(this::onBuy);
        view.addGoBackAction(this::onBack);
        this.currentPort = currentPort;
    }

    /**
     * This method is called when the user clicks the buy button
     */
    private void onBuy() {
        String symbol = view.getSymbol();

        // Checks if user input is a positive integer; if not, prompts for a valid input
        int quantity;
        try {
            quantity = view.getQuantity();
        } catch (NumberFormatException e) {
            view.displayInvalidInputFailure();
            return;
        }

        if (quantity <= 0) {
            view.displayInvalidInputFailure();
            return;
        }

        BuyInputRequest req = new BuyInputRequest(symbol, quantity, currentPort);
        BuyStockController cont = new BuyStockController();

        // Tries to buy the stocks.
        // If there's a problem connecting to the API, displays connection failure.
        // If the portfolio has insufficient balance, displays balance failure.

        BuyOutputResponse res = cont.buyStock(req);

        if (res.getOutput() == null) {
            view.displayConnectionFailure();
        } else if (res.getOutput()) {
            view.displaySuccess();
            view.updateQuantityLabel(quantity);
            view.updateBalanceLabel(currentPort.getBalance());

        } else {
            view.displayBalanceFailure();
        }
    }

    /**
     * This method is called when the user clicks the back button
     */
    private void onBack() {
        view.close();
        new ViewStockPresenter(OuterLayerFactory.instance.getViewStockGUI(view.getSymbol(), currentPort), this.currentPort, this.user);
    }
}
