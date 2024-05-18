package io.github.travisdeshotels.bracketgenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public abstract class BracketType{
    @JsonIgnore
    protected boolean allMatchesNumbered;
    private final String bracketName;
    @JsonIgnore
    private int currentRound;
    private List<Round> rounds;

    protected BracketType() {
        allMatchesNumbered = false;
        bracketName = "";
        rounds = new ArrayList();
    }

    protected BracketType(String bracketName){
        allMatchesNumbered = false;
        this.bracketName = bracketName;
        rounds = new ArrayList();
    }

    abstract boolean buildBracket(List<String> players);

    protected void addRound(Round round){
        rounds.add(round);
    }

    /**
     * @return Returns the number of rounds in the bracket.
     */
    protected int getRoundCount(){
        return rounds.size();
    }

    /**
     * Go to first round in this bracket.
     */
    protected void firstRound(){
        currentRound = 0;
    }

    /**
     * @return Current round of the bracket is returned.
     */
    protected Round getCurrentRound(){
        return rounds.get(currentRound);
    }

    /**
     * Go to next round in the bracket.
     */
    protected void nextRound(){
        currentRound++;
    }

    /**
     * @return True is returned when no other rounds exist
     * in the bracket.
     */
    protected boolean isDone(){
        return currentRound >= rounds.size();
    }
}
