package UseCases.PortfolioCreationUseCase;

import APIInterface.StockAPIRequest;
import APIInterface.StockAPIResponse;
import APIInterface.StockDatabaseGateway;
import db.StockDSResponse;
import db.EntityDBGateway;
import entities.*;
import main.OuterLayerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioSelectedInteractor {
    private final EntityDBGateway dbGateway;
    private final StockFactory stockFactory;

    public PortfolioSelectedInteractor() {
        dbGateway = OuterLayerFactory.instance.getEntityDSGateway();
        stockFactory = new StockFactory();
    }

    public void populatePortfolio(User user, String portfolioName) {
        List<StockDSResponse> stockDSResponses = dbGateway.findPortfolio(portfolioName, user.getUsername()).getStocks();
        List<Stock> stocks = new ArrayList<>();
        StockDatabaseGateway stockDb = OuterLayerFactory.instance.getStockDBGateway();
        StockAPIResponse response;

        for (StockDSResponse stock : stockDSResponses) {
            try {
                response = stockDb.getPrice(new StockAPIRequest(stock.getSymbol()));
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }

            stocks.add(stockFactory.createStock(
                    stock.getSymbol(),
                    response.getPrice(),
                    stock.getQuantity()));
        }

        user.setCurPortfolio(portfolioName);
        user.getCurPortfolio().pullStocks(stocks);
    }

    public void MakeCompPort(User user, String compPort) {
        user.setCompPortfolio(compPort);
        dbGateway.addCompPort(user.getUsername(), compPort);
    }
}