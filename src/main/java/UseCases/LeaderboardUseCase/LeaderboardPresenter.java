package UseCases.LeaderboardUseCase;
import UseCases.PortfolioCreationUseCase.UserPresenter;
import entities.User;
import main.OuterLayerFactory;

import java.util.ArrayList;


public class LeaderboardPresenter {
    private final LeaderboardView view;
    private final User user;

    /**
     * @param view an instance of iLeaderboard GUI for the leaderboard screen
     * @param user the user accessing the leaderboard
     */
    public LeaderboardPresenter(LeaderboardView view, User user) {
        this.view = view;
        this.user = user;
        view.addBackAction(this::onBackAction);
    }

    /**
     * Closes the GUI and opens a UserPresenter which takes the user back to their home screen
     */
    public void onBackAction() {
        view.close();
        new UserPresenter(OuterLayerFactory.instance.getUserGUI(user.getUsername(),
                new ArrayList<>(user.getPortfolioNames()), user.getLastLogin()), user);
    }
}