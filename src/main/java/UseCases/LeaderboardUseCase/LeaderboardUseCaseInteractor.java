package UseCases.LeaderboardUseCase;

import APIInterface.StockAPIGateway;
import APIInterface.StockAPIRequest;
import entities.Leaderboard;
import entities.User;
import main.UserManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardUseCaseInteractor {
    private List<User> users;

    /**
     * @param arr an array of doubles
     * @return the index of the maximum value in the array
     */
    private int indexMax(double[] arr) {
        double maxNum = -1;
        int result = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > maxNum) {
                result = i;
                maxNum = arr[i];
            }
        }
        return result;
    }

    /**
     * @return an array of doubles, with the doubles representing the values of each of the users' competitive
     * portfolio values. The order of the array corresponds to the order of the listOfUsers from the getAllUsers call
     */
    private double[] topValues() {
        users = UserManager.instance.getAllUsers();
        double[] listOfVals = new double[users.size()];
        int i = 0;

        for (User u : users) {
            if (u.getCompPortfolio() != null) {
                StockAPIGateway gateway = new StockAPIGateway();
                for(String stockSym : u.getCompPortfolio().getSymbolToStock().keySet()) {
                    StockAPIRequest request = new StockAPIRequest(stockSym);
                    try {
                        int quantity = u.getCompPortfolio().getSymbolToStock().get(stockSym).getQuantity();
                        listOfVals[i] += gateway.getPrice(request).getPrice()*quantity;
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                listOfVals[i] += u.getCompPortfolio().getBalance();

            } else {
                listOfVals[i] = 0;
            }
            i++;
        }
        return listOfVals;
    }

    /**
     * @return a Leaderboard object with the top users according to the current value of their competitive portfolios
     */
    public Leaderboard updateLeaderboard() {
        double[] listOfVals = topValues();
        Map<User, Double> result = new LinkedHashMap<>();

        for (int j = 0; j < Math.min(Leaderboard.SIZE, listOfVals.length); j++) {
            int indexOfMax = indexMax(listOfVals);
            result.put(users.get(indexOfMax), listOfVals[indexOfMax]);
            listOfVals[indexOfMax] = -1;
        }
        return new Leaderboard(result);
    }
}