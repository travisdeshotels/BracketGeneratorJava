package io.github.travisdeshotels.bracketgenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RoundTest {

    public RoundTest() {
    }

    @Test
    public void testRoundIteratorNoMatches() {
        System.out.println("Iterator: No Matches");
        Round instance = new Round();
        assertTrue(instance.isDone());
        instance.firstMatch();
        assertTrue(instance.isDone());
    }

    @Test
    public void testRoundIteratorOneMatch() {
        System.out.println("Iterator: One Match");
        Match myMatch = new Match("abc", "def");
        Round instance = new Round();
        assertTrue(instance.isDone());
        instance.addMatch(myMatch);
        instance.firstMatch();
        assertFalse(instance.isDone());
        myMatch = instance.getCurrentMatch();
        assertEquals("abc", myMatch.getPlayer1());
        assertEquals("def", myMatch.getPlayer2());
        instance.nextMatch();
        assertTrue(instance.isDone());
    }

    @Test
    public void testRoundIteratorTwoMatches() {
        //create a round and add two matches
        System.out.println("Iterator: Two Matches");
        Match myMatch = new Match("bob", "tom");
        Round instance = new Round();
        instance.addMatch(myMatch);
        myMatch = new Match("rob", "joe");
        instance.addMatch(myMatch);

        //iterate through the matches
        assertFalse(instance.isDone());
        instance.firstMatch();
        myMatch = instance.getCurrentMatch();
        assertEquals("bob", myMatch.getPlayer1());
        assertEquals("tom", myMatch.getPlayer2());
        instance.nextMatch();
        assertFalse(instance.isDone());
        myMatch = instance.getCurrentMatch();
        assertEquals("rob", myMatch.getPlayer1());
        assertEquals("joe", myMatch.getPlayer2());
        instance.nextMatch();
        assertTrue(instance.isDone());
    }
}