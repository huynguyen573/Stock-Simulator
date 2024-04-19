package Controllers;

import UseCases.PortfolioCreationUseCase.PortfolioCreationError;
import UseCases.PortfolioCreationUseCase.PortfolioCreationInteractor;
import UseCases.PortfolioCreationUseCase.PortfolioCreationRequest;
import UseCases.PortfolioCreationUseCase.PortfolioCreationResponse;
import entities.User;

public class PortfolioCreationController {
    private final PortfolioCreationInteractor pInteractor;

    public PortfolioCreationController(User user) {
        pInteractor = new PortfolioCreationInteractor(user);
    }
    /**
     * This method is used to create a portfolio by taking in a param of type PortfolioCreationRequest
     * and using the interactor to process this request, returning a PortfolioCreationResponse
     * @param request the request to make a new portfolio which contains the name
     * @return PortfolioCreationResponse
     */
    public PortfolioCreationResponse createPortfolio(PortfolioCreationRequest request) {
        PortfolioCreationError portfolioCreated = pInteractor.makeNewPortfolio(request.portfolioName());

        return new PortfolioCreationResponse(portfolioCreated);
    }
}