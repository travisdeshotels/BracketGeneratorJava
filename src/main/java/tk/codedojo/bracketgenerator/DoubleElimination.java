package tk.codedojo.bracketgenerator;
import java.util.*;


public class DoubleElimination extends BracketType{

    protected DoubleElimination() {
        super();
    }

    protected DoubleElimination(String divisionName){
        super(divisionName);
    }
    @Override
    protected boolean buildBracket(List<String> players){
        //TODO: implement this function to add support for double elimination brackets
        return false;
    }
}
