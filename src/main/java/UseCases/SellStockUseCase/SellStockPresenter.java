package UseCases.SellStockUseCase;

import Controllers.SellStockController;
import UseCases.SearchStockUseCase.ViewStockPresenter;
import entities.Portfolio;
import entities.User;
import main.OuterLayerFactory;


public class SellStockPresenter {
    /**
     * This class is used to process the data from the model and display it to the user
     */
    private final SellStockView view;
    private final Portfolio portfolio;
    private final SellStockController controller;
    private final User user;

    public SellStockPresenter(SellStockView view, Portfolio portfolio, User user) {
        this.view = view;
        this.user = user;
        controller = new SellStockController();
        this.portfolio = portfolio;
        view.addSellAction(this::onSell);
        view.addGoBackAction(this::onBack);
    }
    /**
     * This method is called when the user clicks the back button
     */
    private void onBack() {
        view.close();
        new ViewStockPresenter(OuterLayerFactory.instance.getViewStockGUI(view.getSymbol(), portfolio), this.portfolio, this.user);
    }

    /**
     * This method is used to process the data given by the user and output the response to the user
     */
    private void onSell() {
        String symbol = view.getSymbol();
        try {
            int quantity = view.getQuantity();
            SellOutputResponse response = controller.sellStock(new SellInputRequest(portfolio, symbol, quantity));
            if (response.possible()) {
                view.displaySuccess();
                view.updateQuantityLabel(quantity);
            } else {
                view.displayQuantityFailure();
            }
        } catch (NumberFormatException e) {
            view.displayQuantityFailure();
        } catch (Exception e) {
            view.displayConnectionFailure();
        }
    }
}