package UseCases.PortfolioCreationUseCase;

import entities.User;

public class CompPortRequest {
    private final User user;
    private final String compPort;

    public CompPortRequest(User user, String compPort) {
        this.user = user;
        this.compPort = compPort;
    }

    public User getUser() {
        return user;
    }

    public String getCompPort() {
        return compPort;
    }
}