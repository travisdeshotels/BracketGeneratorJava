package io.github.travisdeshotels.bracketgenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Round {
    private List<Match> matches;
    private int currentMatch;

    protected Round() {
        matches = new ArrayList();
        currentMatch = 0;
    }

    /**
     * Constructor that creates a round of given size.
     * @param size The number of matches that make up the round.
     */
    protected Round(int size) {
        matches = new ArrayList(size);
        currentMatch = 0;
    }

    protected void addMatch(Match aMatch){
        matches.add(aMatch);
    }

    /**
     * @return Returns the number of matches in the round.
     */
    protected int getMatchCount() {
        return matches.size();
    }

    /**
     * Go to first match in this round.
     */
    protected void firstMatch(){
        currentMatch = 0;
    }

    /**
     * @return Current match of the round is returned.
     */
    protected Match getCurrentMatch(){
        return matches.get(currentMatch);
    }

    /**
     * Go to next match in the round.
     */
    protected void nextMatch() {
        currentMatch++;
    }

    /**
     * @return True is returned when no other matches exist
     * in the round.
     */
    protected boolean isDone(){
        return currentMatch >= matches.size();
    }

    @Override
    public String toString() {
        return "Round{" +
                "matches=" + matches +
                '}';
    }
}
