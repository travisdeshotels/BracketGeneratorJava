package tk.codedojo.bracketgenerator.fntest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    strict = true,
    features = "src/test/java/tk/codedojo/bracketgenerator/fntest/features",
    glue = "tk.codedojo.bracketgenerator.steps",
    tags = "not @functional"
)
public class RunCucumberTest{

}