package tk.codedojo.bracketgenerator.driver;

import org.apache.log4j.Logger;
import tk.codedojo.bracketgenerator.ProcessCSVFile;

import java.io.IOException;

public class Driver {

    private static ProcessCSVFile processCSVFile;
    private static Logger logger = Logger.getLogger(Driver.class.getName());

    private Driver(){
    }

    public static void main(String[] args){
        try {
            Driver.processCSVFile = new ProcessCSVFile();
            processCSVFile.processFile();
        } catch (IOException e) {
            Driver.logger.fatal("Cannot process input file!", e);
        }
    }
}
