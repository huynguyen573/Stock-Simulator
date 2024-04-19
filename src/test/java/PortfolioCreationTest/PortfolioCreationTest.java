package PortfolioCreationTest;


import Controllers.PortfolioCreationController;
import UseCases.LoginUseCase.UserLoginInteractor;
import UseCases.PortfolioCreationUseCase.PortfolioCreationError;
import UseCases.PortfolioCreationUseCase.PortfolioCreationRequest;
import UseCases.PortfolioCreationUseCase.PortfolioCreationResponse;
import UseCases.RegisterUseCase.RegisterInteractor;
import db.EntityDBGateway;
import entities.User;

import main.OuterLayerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;


public class PortfolioCreationTest {

    private static PortfolioCreationController controller;

    private static final String username = "PortCreationUser";
    private static final String password = "TestPassword";

    private static final String portfolioName = "Portfolio1";
    private static User user;

    @BeforeAll
    public static void setUp(){
        EntityDBGateway dbGateway = OuterLayerFactory.instance.getEntityDSGateway();
        dbGateway.deleteUser(username);

        RegisterInteractor registerInteractor = new RegisterInteractor();
        registerInteractor.signUpUser(username, password, password, Date.valueOf(LocalDate.now()));

        UserLoginInteractor loginInteractor = new UserLoginInteractor();
        user = loginInteractor.loginUser(username, password, Date.valueOf(LocalDate.now()));

        controller = new PortfolioCreationController(user);
    }


    /**
     * Test that after processing a new create-portfolio request, the portfolio is added to the user.
     */
    @Test
    public void testNewPortfolio(){
        PortfolioCreationRequest request = new PortfolioCreationRequest(portfolioName);
        PortfolioCreationResponse response = controller.createPortfolio(request);

        Assertions.assertEquals(response.portfolioCreated(), PortfolioCreationError.NONE);
        Assertions.assertTrue(user.getPortfolioNames().contains(portfolioName));
    }

    /**
     * Test that when a portfolio name already exists, processing another request with the same name would get a
     * response indicating duplicate name
     */
    @Test
    public void testDupePortfolio() {
        PortfolioCreationRequest request = new PortfolioCreationRequest(portfolioName);
        controller.createPortfolio(request);
        Assertions.assertTrue(user.getPortfolioNames().contains(portfolioName));

        PortfolioCreationResponse response2 = controller.createPortfolio(request);
        Assertions.assertTrue(user.getPortfolioNames().contains(portfolioName));
        Assertions.assertEquals(response2.portfolioCreated(), PortfolioCreationError.DUPLICATE_NAME);
    }

    /**
     * Test that processing a request with empty name would get a response indicating invalid name
     */
    @Test
    public void testNoName() {
        PortfolioCreationRequest request = new PortfolioCreationRequest("");
        PortfolioCreationResponse response = controller.createPortfolio(request);
        Assertions.assertEquals(response.portfolioCreated(), PortfolioCreationError.INVALID_NAME);
    }
}