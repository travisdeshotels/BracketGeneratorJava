
package io.github.travisdeshotels.bracketgenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Match {
    private String player1;
    private String player2;
    private int matchNumber;    //the match number seen by end users

    protected Match() {
        player1 = "NULL";
        player2 = "NULL";
    }

    /**
     * Constructor that creates a match with two player names.
     * @param player1 First player of the match.
     * @param player2 Second player of the match.
     */
    protected Match(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Adds a player to the match. Only accepts two players.
     * @param playerName is the name to be added.
     * It cannot be blank.
     * @return True is returned when the player
     * is added successfully.
     */
    protected boolean addPlayer(String playerName) {
        if ("".equals(playerName)){
            return false;
        } else if ("NULL".equals(player1)) {
            player1 = playerName;
            return true;
        } else if ("NULL".equals(player2)) {
            player2 = playerName;
            return true;
        } else {
            //already have two players
            //error
            return false;
        }
    }

    /**
     * Check player names for NULL. Both names are NULL
     * for placeholder matches.
     * @return True when both names are NULL.
     */
    protected boolean playerNamesAreNull() {
        return "NULL".equals(player1) && "NULL".equals(player2);
    }
}
