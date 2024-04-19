package Controllers;

import UseCases.SellStockUseCase.SellInputRequest;
import UseCases.SellStockUseCase.SellOutputResponse;
import UseCases.SellStockUseCase.SellUseCaseInteractor;

public class SellStockController {
    /**
     * Create a controller object for the sell stock use case
     */

    public SellStockController() {
    }
    /**
     * This method is used to create a response for the sell stock use case
     * @param sell the request object for the sell stock use case
     * @return the response object for the sell stock use case
     */

    public SellOutputResponse sellStock(SellInputRequest sell){
        SellUseCaseInteractor interactor = new SellUseCaseInteractor();
        return interactor.sellStock(sell);
    }


}