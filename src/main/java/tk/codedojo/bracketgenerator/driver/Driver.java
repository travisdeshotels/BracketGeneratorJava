package tk.codedojo.bracketgenerator.driver;

import tk.codedojo.bracketgenerator.ProcessCSVFile;
import tk.codedojo.bracketgenerator.exception.MissingArgumentsException;

import java.io.IOException;

public class Driver {

    private static ProcessCSVFile processCSVFile;

    private Driver(){
    }

    public static void main(String[] args) throws MissingArgumentsException, IOException {
        if(args.length < 2){
            throw new MissingArgumentsException("Input and Output filenames must be provided!");
        }
        Driver.processCSVFile = new ProcessCSVFile(args[0], args[1]);
        processCSVFile.processFile();
    }
}
