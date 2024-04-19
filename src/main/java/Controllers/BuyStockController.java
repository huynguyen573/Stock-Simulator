package Controllers;

import UseCases.BuyStockUseCase.BuyInputRequest;
import UseCases.BuyStockUseCase.BuyOutputResponse;
import UseCases.BuyStockUseCase.BuyUseCaseInteractor;

public class BuyStockController {
    /**
     * Controller object for the buy stock use case
     */

    public BuyStockController() {
    }
    /**
     * This method is used to create a response for the buy stock use case
     * @param req the request object for the buy stock use case
     * @return the response object for the buy stock use case
     */
    public BuyOutputResponse buyStock(BuyInputRequest req) {
        BuyUseCaseInteractor interactor = new BuyUseCaseInteractor();
        return interactor.buyStock(req);
    }
}