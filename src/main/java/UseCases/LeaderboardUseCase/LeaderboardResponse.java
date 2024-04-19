package UseCases.LeaderboardUseCase;

import entities.Leaderboard;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardResponse {
    private final Leaderboard board;

    public LeaderboardResponse(Leaderboard board) {
        this.board = board;
    }

    /**
     * @return a representation of the LeaderboardResponse in the form of a List of Strings, with the Strings
     * representing the names of the top users, in order of position on the board
     */
    public List<String> toStringList() {
        List<String> result = new ArrayList<>();
        int position = 1;
        for (User u : board.getTopUsers().keySet()) {
            if(u.getUsername().length() < 25) {
                result.add(position + ". " + u.getUsername() + ":" + new String(new char[20 - u.getUsername().length()]).replace("\0", " ") + "$" + board.getTopUsers().get(u));
            } else {
                result.add(position + ". " + u.getUsername() + ":           $" + board.getTopUsers().get(u));
            }
                position++;
        }
        return result;
    }
}