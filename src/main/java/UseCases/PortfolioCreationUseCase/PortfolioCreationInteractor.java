package UseCases.PortfolioCreationUseCase;

import db.PortfolioDSRequest;
import db.EntityDBGateway;
import entities.PortfolioFactory;
import entities.User;
import main.OuterLayerFactory;

/**
 * This class is the use case interactor for the portfoliocreation
 * which contains one method that gets user from the
 * user manager based on given input
 */
public class PortfolioCreationInteractor {
    private final User user;
    private final EntityDBGateway dbGateway;

    public PortfolioCreationInteractor(User user) {
        this.user = user;
        dbGateway = OuterLayerFactory.instance.getEntityDSGateway();
    }

    /**
     * A method that generates a response based on the request
     * @return PortfolioCreationError type such that it can be an empty name error, duplicate name error or no error
     */
    public PortfolioCreationError makeNewPortfolio(String newPortfolioName) {
        if (newPortfolioName.equals("")) {
            return PortfolioCreationError.INVALID_NAME;
        }
        else if (user.getPortfolioNames().contains(newPortfolioName)) {
            return PortfolioCreationError.DUPLICATE_NAME;
        }

        user.addPortfolio(newPortfolioName);
        dbGateway.addPortfolio(new PortfolioDSRequest(newPortfolioName, PortfolioFactory.BALANCE, user.getUsername()));

        return PortfolioCreationError.NONE;

    }
}