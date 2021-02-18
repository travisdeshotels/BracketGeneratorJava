package tk.codedojo.bracketgenerator.steps;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import tk.codedojo.bracketgenerator.SingleElimination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class FunctionalSteps {
    private static final String CSV_FILE = "csvout";
    private static final String SCRIPT_OUTPUT_FILE = "jsonout";
    private static final String JAR_FILE = "BracketGenerator-1.0-shaded.jar";

    private List<SingleElimination> brackets;

    @After
    public void cleanup(){
        File clean = new File(CSV_FILE);
        File clean2 = new File(SCRIPT_OUTPUT_FILE);
        if(clean.delete() && clean2.delete()){
            System.out.println("Cleanup succeeded");
        }
    }

    @Given("I have the following data in my input file")
    public void iHaveTheFollowingDataInMyInputFile(DataTable table) throws Exception {
        List<List<String>> rows = table.asLists(String.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE));
        for(int i=1;i< rows.size();i++){
            List<String> row = rows.get(i);
            writer.write(row.get(0)+','+row.get(1)+','+row.get(2)+"\n");
        }
        writer.close();
    }

    @When("I run the script in the command line")
    public void iRunTheScriptInTheCommandLine() throws Exception {
        String cmd = "java -jar target/" + JAR_FILE + " " + CSV_FILE + " " + SCRIPT_OUTPUT_FILE;
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        if(pr.exitValue() == 1){
            System.err.println("Script failed");
            throw new Exception("Script failed to run!");
        }
    }

    @And("I read the output file")
    public void iReadTheOutputFile() throws Exception {
        JsonMapper mapper = new JsonMapper();
        this.brackets = Arrays.asList(mapper.readValue(new File(SCRIPT_OUTPUT_FILE), SingleElimination[].class).clone());
    }

    @Then("I verify that the data in Division A matches")
    public void iVerifyThatTheDataInDivisionAMatches(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        Assert.assertEquals("A",this.brackets.get(0).getBracketName());
        // Round 1, Match 1
        Assert.assertEquals(Integer.parseInt(rows.get(1).get(0)),this.brackets.get(0).getRounds().get(0).getMatches().get(0).getMatchNumber());
        Assert.assertEquals(rows.get(1).get(1),this.brackets.get(0).getRounds().get(0).getMatches().get(0).getPlayer1());
        Assert.assertEquals(rows.get(1).get(2),this.brackets.get(0).getRounds().get(0).getMatches().get(0).getPlayer2());
        // Round 1, Match 2
        Assert.assertEquals(Integer.parseInt(rows.get(2).get(0)),this.brackets.get(0).getRounds().get(0).getMatches().get(1).getMatchNumber());
        Assert.assertEquals(rows.get(2).get(1),this.brackets.get(0).getRounds().get(0).getMatches().get(1).getPlayer1());
        Assert.assertEquals(rows.get(2).get(2),this.brackets.get(0).getRounds().get(0).getMatches().get(1).getPlayer2());
        // Round 2, Match 1
        Assert.assertEquals(Integer.parseInt(rows.get(3).get(0)),this.brackets.get(0).getRounds().get(1).getMatches().get(0).getMatchNumber());
        Assert.assertEquals(rows.get(3).get(1),this.brackets.get(0).getRounds().get(1).getMatches().get(0).getPlayer1());
        Assert.assertEquals(rows.get(3).get(2),this.brackets.get(0).getRounds().get(1).getMatches().get(0).getPlayer2());
    }

    @And("I verify that the data in Division B matches")
    public void iVerifyThatTheDataInDivisionBMatches(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        Assert.assertEquals("B",this.brackets.get(1).getBracketName());
        // Round 1, Match 1
        Assert.assertEquals(Integer.parseInt(rows.get(1).get(0)),this.brackets.get(1).getRounds().get(0).getMatches().get(0).getMatchNumber());
        Assert.assertEquals(rows.get(1).get(1),this.brackets.get(1).getRounds().get(0).getMatches().get(0).getPlayer1());
        Assert.assertEquals(rows.get(1).get(2),this.brackets.get(1).getRounds().get(0).getMatches().get(0).getPlayer2());
        // Round 1, Match 2
        Assert.assertEquals(Integer.parseInt(rows.get(2).get(0)),this.brackets.get(1).getRounds().get(0).getMatches().get(1).getMatchNumber());
        Assert.assertEquals(rows.get(2).get(1),this.brackets.get(1).getRounds().get(0).getMatches().get(1).getPlayer1());
        Assert.assertEquals(rows.get(2).get(2),this.brackets.get(1).getRounds().get(0).getMatches().get(1).getPlayer2());
        // Round 2, Match 1
        Assert.assertEquals(Integer.parseInt(rows.get(3).get(0)),this.brackets.get(1).getRounds().get(1).getMatches().get(0).getMatchNumber());
        Assert.assertEquals(rows.get(3).get(1),this.brackets.get(1).getRounds().get(1).getMatches().get(0).getPlayer1());
        Assert.assertEquals(rows.get(3).get(2),this.brackets.get(1).getRounds().get(1).getMatches().get(0).getPlayer2());
    }
}
