package io.github.travisdeshotels.bracketgenerator;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.travisdeshotels.bracketgenerator.exception.BadBracketDataException;

public class ProcessCSVFile {
    private static final Logger logger = LogManager.getLogger(ProcessCSVFile.class.getName());
    private static final BracketManager bracketManager = BracketManager.getInstance();
    private final String inputFilename;
    private final String outputFilename;

    public ProcessCSVFile(String inputFilename, String outputFilename){
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
    }

    public void processFile() throws IOException {
        String line;
        BufferedReader bufferedReader;
        FileReader fileReader;
        fileReader = new FileReader(this.inputFilename);
        bufferedReader = new BufferedReader(fileReader);
        line = bufferedReader.readLine();

        while (line != null) {
            this.processLine(line);
            line = bufferedReader.readLine();
        }
        fileReader.close();
        bufferedReader.close();

        this.outputJSON();
    }

    private void processLine(String line){
        List<String> players = new ArrayList<>();
        String[] data = line.split(",");
        String division = data[0];
        String bracketType = data[1];
        String player;

        for (int i=2;i<data.length;i++){
            player = data[i];
            if (!"".equals(player)) {
                players.add(player);
            }
        }
        try {
            ProcessCSVFile.bracketManager.addBracket(division, bracketType, players);
        } catch (BadBracketDataException e) {
            ProcessCSVFile.logger.fatal("Bad bracket data!", e);
        }
    }

    private void outputJSON(){
        ProcessCSVFile.bracketManager.numberMatches();
        OutputJSON outputJSON = new OutputJSON();
        ProcessCSVFile.bracketManager.setupOutput(outputJSON);
        outputJSON.outputBrackets(this.outputFilename);
    }
}
