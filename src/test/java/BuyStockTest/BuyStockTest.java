package BuyStockTest;

import UseCases.BuyStockUseCase.BuyInputRequest;
import UseCases.BuyStockUseCase.BuyOutputResponse;
import UseCases.BuyStockUseCase.BuyUseCaseInteractor;
import UseCases.LoginUseCase.UserLoginInteractor;
import UseCases.PortfolioCreationUseCase.PortfolioCreationInteractor;
import UseCases.PortfolioCreationUseCase.PortfolioSelectedInteractor;
import UseCases.RegisterUseCase.RegisterInteractor;
import db.EntityDBGateway;
import entities.Portfolio;
import entities.Stock;
import entities.User;
import main.OuterLayerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Map;

public class BuyStockTest {

    private static Portfolio port;
    private static final String symbol = "TSLA";
    private static final String symbol2 = "AMZN";
    private static BuyUseCaseInteractor interactor;
    private static final String username = "BuyTestUser";


    @BeforeAll
    public static void setUp() {
        Date date = new Date(100);
        RegisterInteractor interactor1 = new RegisterInteractor();

        EntityDBGateway dbGateway = OuterLayerFactory.instance.getEntityDSGateway();
        dbGateway.deleteUser(username);

        interactor1.signUpUser(username, "password", "password", date);

        UserLoginInteractor interactor2 = new UserLoginInteractor();
        User user = interactor2.loginUser(username, "password", date);

        PortfolioCreationInteractor interactor3 = new PortfolioCreationInteractor(user);
        interactor3.makeNewPortfolio("newPortfolio");

        PortfolioSelectedInteractor interactor4 = new PortfolioSelectedInteractor();
        interactor4.populatePortfolio(user, "newPortfolio");

        port = user.getPortfolio("newPortfolio");

        interactor = new BuyUseCaseInteractor();
    }

    /**
     * Test that the user can buy a stock and that the stock is added to the portfolio
     */
    @Test
    public void buyTSLAStockTest() {
        BuyInputRequest req = new BuyInputRequest(symbol, 5,port);
        interactor.buyStock(req);

        Map<String, Stock> map = port.getSymbolToStock();

        Stock stock = map.get("TSLA");

        assert stock.getQuantity() == 5;
    }

    /**
     * Tests an invalid case where user does not have enough money to buy stock
     */
    @Test
    public void buyStockInsufficientFundsTest() {
        BuyInputRequest req = new BuyInputRequest(symbol2, 1000, port);
        BuyOutputResponse res = interactor.buyStock(req);

        Map<String, Stock> map = port.getSymbolToStock();

        Stock stock = map.get("AMZN");

        assert !res.getOutput();
        assert stock == null;
    }
}