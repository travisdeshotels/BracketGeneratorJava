package tk.codedojo.bracketgenerator;
import java.util.*;


public class SingleElimination extends BracketType{
    private boolean firstRoundDone = false;
    private List<String> players;

    protected SingleElimination(){
        super();
    }

    protected SingleElimination(String divisionName) {
        super(divisionName);
    }

    private boolean isRoundDone(int playerCount){
        return playerCount >= 2;
    }

    @Override
    protected boolean buildBracket(List<String> players){
        int playerCount;
        this.players = players;

        if (this.players.size() % 2 == 1){
            this.players.add("NULL");
        }
        playerCount = this.players.size();
        do {
            addRound(buildRound(playerCount));
            if (!this.firstRoundDone) {
                this.firstRoundDone = true;
            }
            playerCount++;
            playerCount /= 2;
        } while (this.isRoundDone(playerCount));
        return true;
    }

    private String getPlayerName(int playerIndex){
        if (this.firstRoundDone){
            return "NULL";
        } else{
            return this.players.get(playerIndex);
        }
    }

    private Round buildRound(int playerCount){
        Round round;
        int i;

        round = new Round(playerCount / 2);
        for (i = 0; i < playerCount; i += 2) {
            buildMatch(round, i);
        }
        return round;
    }

    private void buildMatch(Round round, int i) {
        String player1 = this.getPlayerName(i);
        String player2 = this.getPlayerName(i + 1);
        round.addMatch(new Match(player1, player2));
    }
}
