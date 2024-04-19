package SellStockTest;

import APIInterface.StockAPIGateway;
import APIInterface.StockAPIRequest;
import APIInterface.StockAPIResponse;
import UseCases.LoginUseCase.UserLoginInteractor;
import UseCases.PortfolioCreationUseCase.PortfolioCreationInteractor;
import UseCases.RegisterUseCase.RegisterInteractor;
import UseCases.SellStockUseCase.SellInputRequest;
import UseCases.SellStockUseCase.SellOutputResponse;
import UseCases.SellStockUseCase.SellUseCaseInteractor;
import db.EntityDBGateway;
import entities.Portfolio;
import entities.Stock;
import entities.User;
import main.OuterLayerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;

public class SellStockTest {

    private static SellUseCaseInteractor interactor;
    private static Portfolio portfolio;
    private static final String symbol = "AAPL";
    private static final int quantity = 10;
    private static final int sellQuant = 4;
    private static final int invalidQuant = 100;
    private static final int negativeQuant = -1;
    private static final String username = "SellTestUser";
    private static final DecimalFormat df = new DecimalFormat("0.00");


    @BeforeAll
    public static void SetUp() throws IOException {
        Date date = new Date(100);

        EntityDBGateway dbGateway = OuterLayerFactory.instance.getEntityDSGateway();
        dbGateway.deleteUser(username);

        RegisterInteractor interactor1 = new RegisterInteractor();
        interactor1.signUpUser(username, "password", "password", date);

        UserLoginInteractor interactor2 = new UserLoginInteractor();
        User user = interactor2.loginUser(username, "password", date);

        PortfolioCreationInteractor interactor3 = new PortfolioCreationInteractor(user);

        interactor3.makeNewPortfolio("newPortfolio");


        StockAPIGateway access = new StockAPIGateway();
        StockAPIResponse res = access.getPrice(new StockAPIRequest(symbol));
        double price = res.getPrice();
        portfolio = user.getPortfolio("newPortfolio");
        portfolio.addStock(symbol, price, quantity);
        interactor = new SellUseCaseInteractor();
    }

    /**
     * Testing the sellStock method in SellUseCaseInteractor to see if the balance and quantity of the stock are updated correctly
     */
    @Test
    public void testSellStock(){
        double balance = portfolio.getBalance();
        SellInputRequest sell = new SellInputRequest(portfolio, symbol, sellQuant);
        Map<String, Stock> map = portfolio.getSymbolToStock();
        interactor.sellStock(sell);
        Stock stock = map.get(symbol);
        assert stock.getQuantity() == quantity - sellQuant;
        assert df.format(portfolio.getBalance()).equals(df.format(balance + sellQuant * stock.getValue()));
    }

    /**
     * Testing an invalid case where the user tries to sell more stocks than they have
     */
    @Test
    public void testInvalidSellStock(){
        double balance = portfolio.getBalance();
        SellInputRequest sell = new SellInputRequest(portfolio, symbol, invalidQuant);
        SellOutputResponse response = interactor.sellStock(sell);
        interactor.sellStock(sell);
        assert portfolio.getBalance() == balance;

        assert Objects.equals(response.getMessage(), "Please enter a valid amount.");

    }

    /**
     * Testing an invalid case where the user tries to sell a negative amount of stocks
     */
    @Test
    public void testNegativeQuantity(){
        double balance = portfolio.getBalance();
        SellInputRequest sell = new SellInputRequest(portfolio, symbol, negativeQuant);
        SellOutputResponse response = interactor.sellStock(sell);
        interactor.sellStock(sell);
        assert portfolio.getBalance() == balance;

        assert Objects.equals(response.getMessage(), "Please enter a positive quantity.");
    }

    /**
     * Testing an invalid case where the user tries to sell a stock that they don't have
     */
    @Test
    public void testNoStock(){
        double balance = portfolio.getBalance();
        SellInputRequest sell = new SellInputRequest(portfolio, "TSLA", sellQuant);
        interactor.sellStock(sell);
        SellOutputResponse response = interactor.sellStock(sell);

        assert portfolio.getBalance() == balance;

        assert Objects.equals(response.getMessage(), "You do not own any of this stock");

    }
}