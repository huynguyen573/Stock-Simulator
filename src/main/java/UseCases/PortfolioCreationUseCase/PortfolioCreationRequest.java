package UseCases.PortfolioCreationUseCase;

/**
 * Request model object for when user attempts to make a new portfolio, contains only a string portfolioName
 */
public final class PortfolioCreationRequest {
    private final String portfolioName;

    public PortfolioCreationRequest(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String portfolioName() {
        return portfolioName;
    }
}