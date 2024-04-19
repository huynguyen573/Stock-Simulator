package UseCases.PortfolioCreationUseCase;

public interface UserView {
    void addLogoutAction(Runnable onLogout);

    void addPortfolioSelectedAction(Runnable onPortfolioSelected);

    void createPortfolioAction(Runnable onCreatePortfolio);
    void goToLeaderboardAction(Runnable onGoToLeaderboard);

    String getPortfolioSelected();

    void close();

    boolean confirmPortfolioMessage(String portfolioName);
}