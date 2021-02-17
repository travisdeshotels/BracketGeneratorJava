package tk.codedojo.bracketgenerator.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FunctionalSteps {
    private static final String CSV_FILE = "csvout";
    private static final String SCRIPT_OUTPUT_FILE = "jsonout";
    private static final String JAR_FILE = "BracketGeneratorJava-1.0-shaded.jar";

    @After
    public void cleanup(){
        File clean = new File(CSV_FILE);
        if(clean.delete()){
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
        if(pr.exitValue() == 0){
            System.out.println("Script ran successfully");
        } else{
            System.err.println("Script failed");
            throw new Exception("Script failed to run!");
        }
    }

    @And("I read the output file")
    public void iReadTheOutputFile() {

    }
}
