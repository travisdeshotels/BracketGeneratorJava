package tk.codedojo.bracketgenerator;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


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
        assertTrue(myMatch.getPlayer1().equals("abc"));
        assertTrue(myMatch.getPlayer2().equals("def"));
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
        assertTrue(myMatch.getPlayer1().equals("bob"));
        assertTrue(myMatch.getPlayer2().equals("tom"));
        instance.nextMatch();
        assertFalse(instance.isDone());
        myMatch = instance.getCurrentMatch();
        assertTrue(myMatch.getPlayer1().equals("rob"));
        assertTrue(myMatch.getPlayer2().equals("joe"));
        instance.nextMatch();
        assertTrue(instance.isDone());
    }
}