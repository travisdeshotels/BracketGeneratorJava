package tk.codedojo.bracketgenerator;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    strict = true,
    features = "src/fn-test/java/tk/codedojo/bracketgenerator/features",
    glue = "tk.codedojo.bracketgenerator.steps",
    tags = "not @skip"
)
public class RunCucumberTest{

}