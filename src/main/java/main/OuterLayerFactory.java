package main;

import APIInterface.StockAPIGateway;
import APIInterface.StockDatabaseGateway;
import UseCases.BuyStockUseCase.BuyStockGUI;
import UseCases.BuyStockUseCase.PortfolioGUI;
import UseCases.BuyStockUseCase.BuyStockView;
import UseCases.BuyStockUseCase.PortfolioView;
import UseCases.LeaderboardUseCase.LeaderboardGUI;
import UseCases.LeaderboardUseCase.LeaderboardView;
import UseCases.LoginUseCase.UserLoginGUI;
import UseCases.LoginUseCase.UserLoginView;
import UseCases.PortfolioCreationUseCase.PortfolioCreationGUI;
import UseCases.PortfolioCreationUseCase.UserGUI;
import UseCases.PortfolioCreationUseCase.PortfolioCreationView;
import UseCases.PortfolioCreationUseCase.UserView;
import UseCases.RegisterUseCase.RegistrationPage;
import UseCases.RegisterUseCase.RegisterView;
import UseCases.SearchStockUseCase.ViewStockGUI;
import UseCases.SearchStockUseCase.ViewStockView;
import UseCases.SellStockUseCase.SellStockGUI;
import UseCases.SellStockUseCase.SellStockView;
import db.EntitySQLGateway;
import db.EntityDBGateway;
import entities.Portfolio;

import java.sql.Date;
import java.util.List;

public class OuterLayerFactory {
    public static final OuterLayerFactory instance = new OuterLayerFactory();

    public EntityDBGateway getEntityDSGateway() {
        return new EntitySQLGateway();
    }

    public UserLoginView getUserLoginGUI() {
        return new UserLoginGUI();
    }

    public UserView getUserGUI(String username, List<String> portfolioNames, Date lastLogin) {
        return new UserGUI(username, portfolioNames, lastLogin);
    }

    public PortfolioCreationView getPortfolioCreationGUI() {return new PortfolioCreationGUI();}

    public PortfolioView getPortfolioGUI(Portfolio port, String username, boolean isComp) {
        return new PortfolioGUI(port, username, isComp);
    }

    public RegisterView getRegisterGUI() {
        return new RegistrationPage();
    }

    public BuyStockView getBuyGUI(String symbol, int quantity, double balance) {
        return new BuyStockGUI(symbol, quantity, balance);
    }
    
    public SellStockView getSellGUI(String symbol, int quantity) {
        return new SellStockGUI(symbol, quantity);
    }
    
    public LeaderboardView getLeaderboardGUI(List<String> topUsers) {
        return new LeaderboardGUI(topUsers);
    }

    public ViewStockView getViewStockGUI(String symbol, Portfolio port) { return new ViewStockGUI(symbol, port);}

    public StockDatabaseGateway getStockDBGateway() {
        return new StockAPIGateway();
    }
}