package UseCases.PortfolioCreationUseCase;

/**
 * Response model object for when user attempts to log in, contains PortfolioCreationError which indicates whether
 * a portfolio has been created or an error occurred.
 */
public final class PortfolioCreationResponse {
    private final PortfolioCreationError portfolioCreated;

    public PortfolioCreationResponse(PortfolioCreationError portfolioCreated) {
        this.portfolioCreated = portfolioCreated;
    }

    public PortfolioCreationError portfolioCreated() {
        return portfolioCreated;
    }
}