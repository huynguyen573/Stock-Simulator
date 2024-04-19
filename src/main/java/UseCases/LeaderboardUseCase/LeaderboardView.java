package UseCases.LeaderboardUseCase;

public interface LeaderboardView {
    void addBackAction(Runnable onBackAction);
    void close();
}