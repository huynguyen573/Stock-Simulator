package UseCases.SearchStockUseCase;


import Controllers.ViewStockController;
import UseCases.BuyStockUseCase.BuyStockPresenter;
import UseCases.BuyStockUseCase.PortfolioPresenter;

import UseCases.SellStockUseCase.SellStockPresenter;
import entities.Portfolio;
import entities.User;
import main.OuterLayerFactory;

import yahoofinance.histquotes.Interval;

import javax.swing.*;

import java.io.IOException;


public class ViewStockPresenter {
    /**
     * This class is used to process the data from the model and display it to the user
     */
    private final ViewStockView view;
    private final Portfolio portfolio;
    private final User user;
    private final ViewStockController controller;

    public ViewStockPresenter(ViewStockView view, Portfolio portfolio, User user) {
        this.view = view;
        this.controller = new ViewStockController(this.view.getStockSymbol());
        JOptionPane jop = new JOptionPane();
        jop.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        jop.setMessage("Loading info for stock: " + this.view.getStockSymbol().toUpperCase());
        JDialog dialog = jop.createDialog(null, "Loading Screen");
        new Thread(() -> {
            onLoadGUI();
            dialog.dispose();
        }).start();
        dialog.setVisible(true);

        this.portfolio = portfolio;
        this.user = user;

        //Setting functionality for buttons
        view.addBuyStockAction(this::onBuyStock);
        view.addSellStockAction(this::onSellStock);
        view.addBackAction(this::onBack);
        view.yearlyButtonAction(this::onYearlyButton);
        view.weeklyButtonAction(this::onWeeklyButton);
        view.todayButtonAction(this::onTodayButton);
        view.refreshButtonAction(this::onLoadGUI);

    }

    private void onLoadGUI() {
        try {
            this.controller.searchStock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.view.setHistData(this.controller.getCurrentHistData());
        try {
            this.view.setStockPrice(this.controller.getCurrentPrice());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.view.loadLabels();
        this.view.updateTable(controller.updateTable(Interval.DAILY));
    }

    private void onTodayButton() {
        this.view.updateTable(controller.updateTable(Interval.DAILY));
    }

    private void onWeeklyButton() {
        this.view.updateTable(controller.updateTable(Interval.WEEKLY));
    }

    private void onYearlyButton() {
        this.view.updateTable(controller.updateTable(Interval.MONTHLY));
    }


    private void onBack() {
        boolean isComp = this.portfolio.getName().equals(user.getCompPortfolioName());
        view.close();
        new PortfolioPresenter(OuterLayerFactory.instance.getPortfolioGUI(this.portfolio, this.user.getUsername(), isComp), this.portfolio, this.user);
    }

    private void onSellStock() {
        //call Sell Presenter
        view.close();
        int quantity = portfolio.getStockQuantity(view.getStockSymbol());
        new SellStockPresenter(OuterLayerFactory.instance.getSellGUI(view.getStockSymbol(), quantity), this.portfolio, this.user);
    }

    private void onBuyStock() {
        //Call Buy Presenter
        view.close();
        int quantity = portfolio.getStockQuantity(view.getStockSymbol());
        new BuyStockPresenter(OuterLayerFactory.instance.getBuyGUI(view.getStockSymbol(), quantity, this.portfolio.getBalance()), this.portfolio, this.user);
    }
}