package tk.codedojo.bracketgenerator.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FunctionalSteps {
    @After
    public void cleanup(){
        File clean = new File("out");
        if(clean.delete()){
            System.out.println("Cleanup");
        }
    }

    @Given("I have the following data in my input file")
    public void iHaveTheFollowingDataInMyInputFile(DataTable table) throws Exception {
        List<List<String>> rows = table.asLists(String.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter("out"));
        for(int i=1;i< rows.size();i++){
            List<String> row = rows.get(i);
            writer.write(row.get(0)+','+row.get(1)+','+row.get(2)+"\n");
        }
        writer.close();
    }
}
