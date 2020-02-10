import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cell {
    private String output;
    int x;
    int y;

    public Cell() {
        this.output = "";
    
    }

    public String getOutput() {
        return this.output;
    }
    public void setOutput(String word) {
        this.output = word;
    }

}