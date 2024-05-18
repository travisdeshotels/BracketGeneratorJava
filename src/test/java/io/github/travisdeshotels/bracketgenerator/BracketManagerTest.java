package io.github.travisdeshotels.bracketgenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import io.github.travisdeshotels.bracketgenerator.exception.BadBracketDataException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BracketManagerTest {
    public BracketManager myBracketManager;

    public BracketManagerTest() {
        myBracketManager = BracketManager.getInstance();
    }

    @BeforeEach
    public void setUp() {
        myBracketManager.clearBrackets();
    }

    /**
     * Test of addBracket method, of class BracketManager.
     * Test that BadBracketDataException is thrown for each invalid call to AddBracket.
     * Division Name must be non-blank.
     * Players cannot be null or less than 2.
     * Bracket Type must be "SE" or "DE".
     */
    @Test
    public void testAddBracket(){
        System.out.println("addBracket");
        String divisionName = "";
        String bracketType = "";
        List<String> players;

        try {
            myBracketManager.addBracket(divisionName, bracketType, null);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        try {
            myBracketManager.addBracket("alpha", "SE", null);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        players = new ArrayList<>(2);
        players.add("bob"); players.add("tom");
        try {
            myBracketManager.addBracket("alpha", bracketType, players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        try {
            myBracketManager.addBracket("", "SE", players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        try {
            myBracketManager.addBracket(divisionName, bracketType, players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        players.clear();
        try {
            myBracketManager.addBracket(divisionName, "SE", players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        try {
            myBracketManager.addBracket("alpha", bracketType, players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
        //Test duplicate division names.
        players.add("bob"); players.add("tom");
        divisionName = "useThisTwice";
        try {
            myBracketManager.addBracket(divisionName, "SE", players);
        } catch (Exception e){
            fail();
        }
        try {
            myBracketManager.addBracket(divisionName, bracketType, players);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof BadBracketDataException);
        }
    }

    /**
     * Test of adding a 2-player single elimination bracket.
     */
    @Test
    public void testAddBracket2PlayersSE() throws BadBracketDataException{
        System.out.println("Add 2-player SE bracket");
        String divisionName = "alpha";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        List<String> players = new ArrayList<>(2);
        players.add("who");
        players.add("where");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        assertFalse(myBracketManager.isDone());
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals("alpha", myBracket.getBracketName());
        assertEquals(1, myBracket.getRoundCount());
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        assertFalse(myRound.isDone());
        myMatch = myRound.getCurrentMatch();
        assertEquals("who", myMatch.getPlayer1());
        assertEquals("where", myMatch.getPlayer2());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 2-player double elimination bracket.
     */
    @Disabled
    @Test
    public void testAddBracket2PlayersDE() throws BadBracketDataException{
        System.out.println("Add 2-player DE bracket");
        //Set up bracket.
        String divisionName = "beta";
        String bracketType = "DE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        List<String> players = new ArrayList<>(2);
        players.add("why");
        players.add("whom");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.numberMatches();
        //Test general bracket data.
        myBracketManager.firstBracket();
        assertFalse(myBracketManager.isDone());
        myBracket = myBracketManager.getCurrentBracket();
        System.out.println(myBracket.getRounds().toString());
        assertEquals("beta", myBracket.getBracketName());
        assertEquals(3, myBracket.getRoundCount());
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        assertFalse(myRound.isDone());
        //Test match of first round.
        myMatch = myRound.getCurrentMatch();
        assertEquals("why", myMatch.getPlayer1());
        assertEquals("whom", myMatch.getPlayer2());
        assertEquals(1,myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        //Test match of second round.
        myMatch = myRound.getCurrentMatch();
        assertEquals(2,myMatch.getMatchNumber());
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        //Test match of third round.
        myMatch = myRound.getCurrentMatch();
        assertEquals(3, myMatch.getMatchNumber());
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 3-player single elimination bracket.
     */
    @Test
    public void testAddBracket3PlayersSE() throws BadBracketDataException{
        System.out.println("Add 3-player SE bracket");
        String divisionName = "testing";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        List<String> players = new ArrayList<>(3);
        players.add("1");
        players.add("2");
        players.add("3");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals("testing", myBracket.getBracketName());
        assertEquals(2, myBracket.getRoundCount());
        myBracket.firstRound();
        //first round
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myRound.firstMatch();
        //first match
        myMatch = myRound.getCurrentMatch();
        assertEquals("1", myMatch.getPlayer1());
        assertEquals("2", myMatch.getPlayer2());
        myRound.nextMatch();
        //second match
        myMatch = myRound.getCurrentMatch();
        assertEquals("3", myMatch.getPlayer1());
        assertEquals("NULL", myMatch.getPlayer2());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //second round
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        //first match
        myMatch = myRound.getCurrentMatch();
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 4-player double elimination bracket.
     * This is only verifying the number of matches per round
     * and the number of rounds.
     */
    @Disabled
    @Test
    public void testAddBracket4PlayersDE() throws BadBracketDataException{
        System.out.println("Add 4-player DE bracket");
        String divisionName = "4p_test";
        String bracketType = "DE";
        BracketType myBracket;
        Round myRound;
        List<String> players = new ArrayList<>(4);
        players.add("A"); players.add("B");
        players.add("C"); players.add("D");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals(5, myBracket.getRoundCount());
        myBracket.firstRound();
        //round 1
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 2
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 3
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 4
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        //round 5
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 5-player single elimination bracket.
     */
    @Test
    public void testAddBracket5PlayersSE() throws BadBracketDataException{
        System.out.println("Add 5-player SE bracket");
        String divisionName = "gamma";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        List<String> players = new ArrayList<>(5);
        players.add("ichi"); players.add("ni");
        players.add("san"); players.add("shi");
        players.add("go");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals("gamma", myBracket.getBracketName());
        assertEquals(3, myBracket.getRoundCount());
        myBracket.firstRound();
        //first round
        myRound = myBracket.getCurrentRound();
        assertEquals(3, myRound.getMatchCount());
        myRound.firstMatch();
        //first match
        myMatch = myRound.getCurrentMatch();
        assertEquals("ichi", myMatch.getPlayer1());
        assertEquals("ni", myMatch.getPlayer2());
        myRound.nextMatch();
        //second match
        myMatch = myRound.getCurrentMatch();
        assertEquals("san", myMatch.getPlayer1());
        assertEquals("shi", myMatch.getPlayer2());
        myRound.nextMatch();
        //third match
        myMatch = myRound.getCurrentMatch();
        assertEquals("go", myMatch.getPlayer1());
        assertEquals("NULL", myMatch.getPlayer2());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //second round
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myRound.firstMatch();
        //first match
        myMatch = myRound.getCurrentMatch();
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        //second match
        myMatch = myRound.getCurrentMatch();
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //third round
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myRound.firstMatch();
        //first match
        myMatch = myRound.getCurrentMatch();
        assertTrue(myMatch.playerNamesAreNull());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 7-player double elimination bracket.
     * This is only verifying the number of matches per round
     * and the number of rounds.
     */
    @Disabled
    @Test
    public void testAddBracket7PlayersDE() throws BadBracketDataException{
        System.out.println("Add 7-player DE bracket");
        String divisionName = "7p_test";
        String bracketType = "DE";
        BracketType myBracket;
        Round myRound;
        List<String> players = new ArrayList<>(7);
        players.add("a"); players.add("b"); players.add("c");
        players.add("d"); players.add("e"); players.add("f");
        players.add("g");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals(6, myBracket.getRoundCount());
        myBracket.firstRound();
        //round 1
        myRound = myBracket.getCurrentRound();
        assertEquals(4, myRound.getMatchCount());
        myBracket.nextRound();
        //round 2
        myRound = myBracket.getCurrentRound();
        assertEquals(4, myRound.getMatchCount());
        myBracket.nextRound();
        //round 3
        myRound = myBracket.getCurrentRound();
        assertEquals(3, myRound.getMatchCount());
        myBracket.nextRound();
        //round 4
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 5
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        //round 6
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 9-player single elimination bracket.
     * This is only verifying the number of matches per round
     * and the number of rounds.
     */
    @Test
    public void testAddBracket9PlayersSE() throws BadBracketDataException{
        System.out.println("Add 9-player SE bracket");
        String divisionName = "division";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        List<String> players = new ArrayList<>(9);
        players.add("za"); players.add("yb"); players.add("xc");
        players.add("vd"); players.add("ue"); players.add("tf");
        players.add("rg"); players.add("qh"); players.add("pi");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals(4, myBracket.getRoundCount());
        myBracket.firstRound();
        //round 1
        myRound = myBracket.getCurrentRound();
        assertEquals(5, myRound.getMatchCount());
        myBracket.nextRound();
        //round 2
        myRound = myBracket.getCurrentRound();
        assertEquals(3, myRound.getMatchCount());
        myBracket.nextRound();
        //round 3
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 4
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 13-player single elimination bracket.
     * This is only verifying the number of matches per round
     * and the number of rounds.
     */
    @Test
    public void testAddBracket13PlayersSE() throws BadBracketDataException{
        System.out.println("Add 13-player SE bracket");
        String divisionName = "division";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        List<String> players = new ArrayList<>(13);
        players.add("1331"); players.add("1221"); players.add("1111");
        players.add("1001"); players.add("0990"); players.add("0880");
        players.add("0770"); players.add("0660"); players.add("0550");
        players.add("0440"); players.add("0330"); players.add("0220");
        players.add("0110");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals(4, myBracket.getRoundCount());
        myBracket.firstRound();
        //round 1
        myRound = myBracket.getCurrentRound();
        assertEquals(7, myRound.getMatchCount());
        myBracket.nextRound();
        //round 2
        myRound = myBracket.getCurrentRound();
        assertEquals(4, myRound.getMatchCount());
        myBracket.nextRound();
        //round 3
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 4
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test of adding a 16-player single elimination bracket.
     * This is only verifying the number of matches per round
     * and the number of rounds.
     */
    @Test
    public void testAddBracket16PlayersSE() throws BadBracketDataException{
        System.out.println("Add 16-player SE bracket");
        String divisionName = "division";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        List<String> players = new ArrayList<>(16);
        players.add("z"); players.add("y"); players.add("x"); players.add("w");
        players.add("v"); players.add("u"); players.add("t"); players.add("s");
        players.add("r"); players.add("q"); players.add("p"); players.add("o");
        players.add("n"); players.add("m"); players.add("l"); players.add("k");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        assertEquals(4, myBracket.getRoundCount());
        myBracket.firstRound();
        //round 1
        myRound = myBracket.getCurrentRound();
        assertEquals(8, myRound.getMatchCount());
        myBracket.nextRound();
        //round 2
        myRound = myBracket.getCurrentRound();
        assertEquals(4, myRound.getMatchCount());
        myBracket.nextRound();
        //round 3
        myRound = myBracket.getCurrentRound();
        assertEquals(2, myRound.getMatchCount());
        myBracket.nextRound();
        //round 4
        myRound = myBracket.getCurrentRound();
        assertEquals(1, myRound.getMatchCount());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Simple test of numbering one small bracket.
     */
    @Test
    public void testNumber1Bracket() throws BadBracketDataException{
        System.out.println("Number 4-player SE bracket");
        String divisionName = "fourply";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        List<String> players = new ArrayList<>(4);
        players.add("Robert");
        players.add("Steven");
        players.add("William");
        players.add("Alexander");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.numberMatches();
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(1, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(2, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(3, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        assertTrue(myBracketManager.isDone());
    }

    /**
     * Test 2 brackets and confirm the match numbers.
     */
    @Test
    public void testNumber2Brackets() throws BadBracketDataException{
        System.out.println("Number 2 SE brackets");
        String divisionName = "eightplayers";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        //build bracket 1
        List<String> players = new ArrayList<>(8);
        players.add("bob"); players.add("rob");
        players.add("tob"); players.add("cob");
        players.add("bobbob"); players.add("robrob");
        players.add("tobtob"); players.add("cobcob");
        myBracketManager.addBracket(divisionName, bracketType, players);
        //build bracket 2
        divisionName = "fourplayers";
        players = new ArrayList<>(4);
        players.add("bill");
        players.add("phill");
        players.add("jill");
        players.add("fill");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.numberMatches();
        myBracketManager.firstBracket();
        //check bracket 1, round 1
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(1, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(2, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(3, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(4, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //check bracket 2, round 1
        myBracketManager.nextBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(5, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(6, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //check bracket 1, round 2
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(7, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(8, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //check bracket 2, round 2
        myBracketManager.nextBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(9, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        //check bracket 1, round 3
        myBracketManager.firstBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(10, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
    }

    /**
     * Test the numbering of 4 brackets.
     */
    @Test
    public void testNumber4Brackets() throws BadBracketDataException{
        System.out.println("Number 4 SE brackets");
        String divisionName = "one";
        String bracketType = "SE";
        BracketType myBracket;
        Round myRound;
        Match myMatch;
        //build bracket 1
        List<String> players = new ArrayList<>(4);
        players.add("1"); players.add("2");
        players.add("3"); players.add("4");
        myBracketManager.addBracket(divisionName, bracketType, players);
        //build bracket 2
        divisionName = "two";
        players = new ArrayList<>(2);
        players.add("a");
        players.add("b");
        myBracketManager.addBracket(divisionName, bracketType, players);
        //build bracket 3
        divisionName = "three";
        players = new ArrayList<>(2);
        players.add("c");
        players.add("d");
        myBracketManager.addBracket(divisionName, bracketType, players);
        //build bracket 4
        divisionName = "four";
        players = new ArrayList<>(4);
        players.add("5"); players.add("6");
        players.add("7"); players.add("8");
        myBracketManager.addBracket(divisionName, bracketType, players);
        myBracketManager.numberMatches();
        myBracketManager.firstBracket();
        //check bracket 1, round 1
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(1, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(2, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        //check bracket 2, round 1
        myBracketManager.nextBracket();
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(3, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        //check bracket 3, round 1
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(4, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        //check bracket 4, round 1
        myBracket = myBracketManager.getCurrentBracket();
        myBracket.firstRound();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(5, myMatch.getMatchNumber());
        myRound.nextMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(6, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        myBracketManager.firstBracket();
        //check bracket 1, round 2
        myBracket = myBracketManager.getCurrentBracket();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(7, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
        myBracketManager.nextBracket();
        myBracketManager.nextBracket();
        myBracketManager.nextBracket();
        //check bracket 4, round 2
        myBracket = myBracketManager.getCurrentBracket();
        myRound = myBracket.getCurrentRound();
        myRound.firstMatch();
        myMatch = myRound.getCurrentMatch();
        assertEquals(8, myMatch.getMatchNumber());
        myRound.nextMatch();
        assertTrue(myRound.isDone());
        myBracket.nextRound();
        assertTrue(myBracket.isDone());
    }
}