package UseCases.PortfolioCreationUseCase;

import Controllers.PortfolioCreationController;
import entities.User;
import main.OuterLayerFactory;

import java.util.ArrayList;
/**
 * This class is responsible for controlling the logic of
 * the portfolio creation view
 */
public class PortfolioCreationPresenter {
    private final PortfolioCreationView view;
    private final PortfolioCreationController controller;
    private final User user;

    public PortfolioCreationPresenter(PortfolioCreationView view, User user) {
        this.view = view;
        this.user = user;
        controller = new PortfolioCreationController(user);

        view.addCreatePortfolioAction(this::createPortfolio);
        view.addBackAction(this::onBack);
    }

    /**
     * Contains logic for when user clicks on the create portfolio button.
     * Makes a request containing the string from view input, then processes the request using the controller
     */
    private void createPortfolio(){
        PortfolioCreationRequest request = new PortfolioCreationRequest(view.getNewPortfolioName());

        PortfolioCreationResponse response = controller.createPortfolio(request);

        if (response.portfolioCreated() == PortfolioCreationError.DUPLICATE_NAME) {
            view.presentDuplicateNameError();
        } else if (response.portfolioCreated() == PortfolioCreationError.INVALID_NAME) {
            view.presentNameInvalidError();
        } else {
            onBack();
        }
    }

    /**
     * contains logic for when the user clicks on the back button, ie moves to the user view
     */
    private void onBack(){
        view.close();
        new UserPresenter(
                OuterLayerFactory.instance.getUserGUI(
                        user.getUsername(),
                        new ArrayList<>(user.getPortfolioNames()),
                        user.getLastLogin()),
                user);
    }
}