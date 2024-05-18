package io.github.travisdeshotels.bracketgenerator;

import io.github.travisdeshotels.bracketgenerator.exception.BadBracketDataException;

import java.util.*;



public class BracketManager {
    private static final BracketManager bracketManager = new BracketManager();
    private Map bracketNamesUsed;
    private List<BracketType> brackets;
    private int currentBracket;
    private int i;

    private BracketManager() {
        bracketNamesUsed = new HashMap();
        brackets = new ArrayList();
        currentBracket = 0;
        i = 0;
    }

    /**
     * Adds a bracket to the list.
     *
     * @param divisionName Cannot be empty and must be unique.
     * @param bracketType  SE and DE are currently valid.
     * @param players      This list must contain at least 2 names.
     * @throws BadBracketDataException when given bad bracket data.
     */
    public void addBracket(String divisionName,
                           String bracketType,
                           List<String> players) throws BadBracketDataException {
        BracketType myBracket;
        this.validateBracket(divisionName, players);


        switch (bracketType) {
            case "SE":
                myBracket = new SingleElimination(divisionName);
                break;
            case "DE":
                myBracket = new DoubleElimination(divisionName);
                break;
            default:
                throw new BadBracketDataException("Bracket type is invalid!");
        }
        brackets.add(myBracket);
        myBracket.buildBracket(players);
    }

    private void validateBracket(String divisionName, List<String> players) throws BadBracketDataException {
        if (players == null) {
            throw new BadBracketDataException("Players list cannot be empty!");
        }
        if (players.size() < 2) {
            throw new BadBracketDataException("Divisions must have at least 2 players!");
        }
        if ("".equals(divisionName)) {
            throw new BadBracketDataException("Division names cannot be empty!");
        }
        if (this.bracketNamesUsed.containsKey(divisionName) ){
            throw new BadBracketDataException("Division names can only be used once!");
        } else{
            //A unique division name was given.
            //Add it to bracket names used.
            this.bracketNamesUsed.put(divisionName, true);
        }
    }

    /**
     * Removes all the brackets and resets the state
     * of bracket manager.
     */
    public void clearBrackets() {
        this.brackets.clear();
        this.currentBracket = 0;
        this.i = 0;
        this.bracketNamesUsed = new HashMap();
    }

    public static BracketManager getInstance() {
        return bracketManager;
    }

    /**
     * Numbers all the matches. Used after all brackets
     * have been added.
     */
    public void numberMatches() {
        boolean allBracketsAreNumbered = false;
        BracketType myBracket;

        while (!allBracketsAreNumbered) {
            firstBracket();
            allBracketsAreNumbered = true;
            while (!this.isDone()) {
                myBracket = getCurrentBracket();
                if (!myBracket.allMatchesNumbered) {
                    numberRound(myBracket.getCurrentRound());
                    allBracketsAreNumbered = false;
                }
                myBracket.nextRound();
                if (myBracket.isDone()) {
                    myBracket.allMatchesNumbered = true;
                }
                nextBracket();
            }
        }
    }

    /**
     * Numbers all matches in given round.
     *
     * @param myRound contains matches to be numbered.
     */
    private void numberRound(Round myRound) {
        Match myMatch;

        myRound.firstMatch();
        while (!myRound.isDone()) {
            myMatch = myRound.getCurrentMatch();
            myMatch.setMatchNumber(++i);
            myRound.nextMatch();
        }
    }

    /**
     * Passes brackets to output object so that output may be done.
     *
     * @param outputJSON the output object receiving the brackets.
     */
    public void setupOutput(OutputJSON outputJSON) {
        outputJSON.setBrackets(this.brackets);
    }

    /**
     * Go to first bracket.
     */
    protected void firstBracket() {
        currentBracket = 0;
    }

    /**
     * @return Current bracket is returned.
     */
    protected BracketType getCurrentBracket() {
        return brackets.get(currentBracket);
    }

    /**
     * Go to the next bracket.
     */
    protected void nextBracket() {
        currentBracket++;
    }

    /**
     * @return True is returned when the last bracket
     * has been accessed.
     */
    protected boolean isDone() {
        return currentBracket >= brackets.size();
    }
}
