package tk.codedojo.bracketgenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OutputJSON {

    private List<BracketType> brackets;

    public void setBrackets(List<BracketType> brackets){
        this.brackets = brackets;
    }

    public void outputBrackets(String outFile) {
        ObjectMapper mapper = new ObjectMapper();
        Logger logger = LogManager.getLogger(OutputJSON.class.getName());

        try {
            mapper.writeValue(new File(outFile), brackets);
        } catch (IOException e) {
            logger.fatal("Error saving JSON file!", e);
        }
    }
}