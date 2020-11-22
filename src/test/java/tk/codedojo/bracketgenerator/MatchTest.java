package tk.codedojo.bracketgenerator;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class MatchTest {

    public MatchTest() {
    }

    /**
     * Test of Constructor methods, of class Match.
     */
    @Test
    public void testMatch() {
        System.out.println("match constructor");
        Match instance = new Match();
        assertTrue(instance.playerNamesAreNull());
        instance = new Match("A", "B");
        assertTrue(instance.getPlayer1().equals("A"));
        assertTrue(instance.getPlayer2().equals("B"));
    }

    /**
     * Test of addPlayer method, of class Match.
     */
    @Test
    public void testAddPlayer() {
        System.out.println("addPlayer");
        String playerName = "";
        Match instance = new Match();
        boolean expResult = false;
        boolean result = instance.addPlayer(playerName);
        assertEquals(expResult, result);
        assertTrue(instance.addPlayer("Bob"));
        assertTrue(instance.addPlayer("Tom"));
        assertFalse(instance.addPlayer("Rob"));
    }
}