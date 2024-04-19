package LeaderboardTest;

import UseCases.LeaderboardUseCase.LeaderboardResponse;
import db.PortfolioDSRequest;
import db.EntityDBGateway;
import entities.*;
import main.OuterLayerFactory;
import main.UserManager;
import org.junit.AfterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import UseCases.LeaderboardUseCase.LeaderboardUseCaseInteractor;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

import db.UserDSRequest;

public class LeaderboardTest {
    private static LeaderboardUseCaseInteractor interactor;
    private static EntityDBGateway dbGateway;



    @BeforeAll
    public static void setUp() {
        dbGateway = OuterLayerFactory.instance.getEntityDSGateway();

        dbGateway.deleteUser("testUser1");
        dbGateway.deleteUser("testUser2");

        interactor = new LeaderboardUseCaseInteractor();
        UserDSRequest testUser1 = new UserDSRequest("testUser1", "password", Date.valueOf(LocalDate.now()));
        dbGateway.addUser(testUser1);
        UserDSRequest testUser2 = new UserDSRequest("testUser2", "password", Date.valueOf(LocalDate.now()));
        dbGateway.addUser(testUser2);
        PortfolioDSRequest portfolio1 = new PortfolioDSRequest("Portfolio1", 100001, "testUser1");
        PortfolioDSRequest portfolio2 = new PortfolioDSRequest("Portfolio2", 100000, "testUser2");
        dbGateway.addPortfolio(portfolio1);
        dbGateway.addPortfolio(portfolio2);
        dbGateway.addCompPort("testUser1", "Portfolio1");
        dbGateway.addCompPort("testUser2", "Portfolio2");

    }

    /**
     * Test that the getAllUsers method contains users
     */
    @Test
    public void testGetAllUsersNonEmpty() {
        List<User> users = UserManager.instance.getAllUsers();
        Assertions.assertFalse(users.isEmpty());
    }

    /**
     * Test that the leaderboard displays the correct top users with the highest net values
     */
    @Test
    public void testLeaderboardTwoUsers() {
        dbGateway.updatePortfolioBalance("Portfolio1", 100001, "testUser1");
        dbGateway.updatePortfolioBalance("Portfolio2", 100000, "testUser2");
        Leaderboard board = interactor.updateLeaderboard();
        LeaderboardResponse response = new LeaderboardResponse(board);
        List<String> stringList = response.toStringList();
        assert (stringList.get(0).equals("1. testUser1:           $100001.0"));
        assert (stringList.get(1).equals("2. testUser2:           $100000.0"));
    }

    /**
     * Test that the leaderboard will update when the value of a top user's portfolio changes
     */
    @Test
    public void testLeaderboardUpdate() {
        dbGateway.updatePortfolioBalance("Portfolio2", 100002, "testUser2");
        dbGateway.updatePortfolioBalance("Portfolio1", 100001, "testUser1");
        Leaderboard board = interactor.updateLeaderboard();
        LeaderboardResponse response = new LeaderboardResponse(board);
        List<String> stringList = response.toStringList();
        assert (stringList.get(0).equals("1. testUser2:           $100002.0"));
        assert (stringList.get(1).equals("2. testUser1:           $100001.0"));
    }

    /**
     * Test that the leaderboard will not change positions unless a user's portfolio exceeds the top user's
     * in value, i.e. if they have the same amount, then the one that had that value first remains on top
     */
    @Test
    public void testLeaderboardSameBal() {
        dbGateway.updatePortfolioBalance("Portfolio1", 100003, "testUser1");
        dbGateway.updatePortfolioBalance("Portfolio2", 100003, "testUser2");
        Leaderboard board = interactor.updateLeaderboard();
        LeaderboardResponse response = new LeaderboardResponse(board);
        List<String> stringList = response.toStringList();
        assert (stringList.get(0).equals("1. testUser1:           $100003.0"));
        assert (stringList.get(1).equals("2. testUser2:           $100003.0"));
    }
    @AfterClass
    @Test
    public void endTests() {
        dbGateway.deleteUser("testUser1");
        dbGateway.deleteUser("testUser2");

    }
}