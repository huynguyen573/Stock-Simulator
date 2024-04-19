package UseCases.PortfolioCreationUseCase;

import Controllers.PortfolioSelectedController;
import UseCases.BuyStockUseCase.PortfolioPresenter;
import UseCases.LoginUseCase.UserLoginPresenter;
import entities.User;
import main.OuterLayerFactory;
import UseCases.LeaderboardUseCase.LeaderboardPresenter;
import Controllers.LeaderboardController;
import java.util.List;

public class UserPresenter {
    private final UserView view;
    private final PortfolioSelectedController controller;
    private final User user;
    private final LeaderboardController lController;


    public UserPresenter(UserView view, User user) {
        this.view = view;

        this.user = user;
        controller = new PortfolioSelectedController();
        lController = new LeaderboardController();

        view.addLogoutAction(this::onLogout);
        view.addPortfolioSelectedAction(this::onPortfolioSelected);
        view.createPortfolioAction(this::onCreatePortfolio);
        view.goToLeaderboardAction(this::onGoToLeaderboard);
    }

    private void onLogout() {
        view.close();
        new UserLoginPresenter(OuterLayerFactory.instance.getUserLoginGUI());
    }

    private void onPortfolioSelected() {
        String portfolioName = view.getPortfolioSelected();

        if(!this.view.confirmPortfolioMessage(portfolioName)){return;}

        controller.PopulatePortfolio(new PortfolioSelectedRequest(user, portfolioName));

        boolean isComp = portfolioName.equals(user.getCompPortfolioName());

        view.close();
        new PortfolioPresenter(
                OuterLayerFactory.instance.getPortfolioGUI(
                        user.getPortfolio(portfolioName),
                        user.getUsername(),
                        isComp),
                user.getPortfolio(portfolioName), user);
    }

    private void onCreatePortfolio() {
        view.close();
        new PortfolioCreationPresenter(OuterLayerFactory.instance.getPortfolioCreationGUI(), user);
    }
    private void onGoToLeaderboard() {
        view.close();
        List<String> topUsers = lController.currLeaderboard().toStringList();
        new LeaderboardPresenter(OuterLayerFactory.instance.getLeaderboardGUI(topUsers), user);
    }
}