package tk.codedojo.bracketgenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("A", instance.getPlayer1());
        assertEquals("B", instance.getPlayer2());
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