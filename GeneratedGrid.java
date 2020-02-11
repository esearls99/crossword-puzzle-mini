import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.*;

public class GeneratedGrid {
    public static ArrayList<String> wordbank;
    private static int N;
    public Cell[][] grid;
    int midPoint;
    Visual visual;
    static String lang;
    // boolean graphics;

    public GeneratedGrid(int dimension, String language) {
        N = dimension;
        lang = language;
        if (lang.equals("standard")) {
            wordbank = wordScanner("wordbank.txt", N);
        } else if (lang.equals("names")) {
            wordbank = wordScanner("names.txt", N);
        } else if (lang.equals("acronyms")) {
            wordbank = wordScanner("acronyms.txt", N);
        } else {
            Crossword.showUsageAndExit();
        }
        this.grid = new Cell[N][N];
        this.initializeCells(this.grid);
        this.midPoint = (int) getDimension() / 2;

    }

    public void wordInputWithInit() { // brute force method for inputting words into generated grid

        while (!this.isComplete()) {
            this.createInitialGrid();

            I_LOOP: for (int i = 1; i < getDimension(); i++) {
                int count = 0;
                this.insertWordVert(chooseWord(this.possibleWordsVert(i, wordbank)), i);

                J_LOOP: for (int j = 0; j < getDimension(); j++) {

                    if (this.possibleWords(j, wordbank).isEmpty()) {

                        for (int k = 1; k < getDimension(); k++) {
                            this.removeWordUnder(k);
                        }
                        count = count + 1;

                        if (count == 250) {
                            break I_LOOP;
                        }
                        i = 0;
                        break J_LOOP;
                    }
                }
            }
        }

    }

    public boolean checkInput() {
        boolean success = true;
        for (int i = 0; i < getDimension(); i++) {
            if (this.possibleWords(i, wordbank).isEmpty()) {
                success = false;

            }
        }
        return success;

    }

    public boolean checkGrid() { // verifies that for every possible down or across, there is at least one
                                 // possible word
        boolean success = true;
        for (int i = 0; i < getDimension(); i++) {
            if (this.possibleWords(i, wordbank).isEmpty()) {
                success = false;

            }
            if (this.possibleWordsVert(i, wordbank).isEmpty()) {
                success = false;
            }
        }

        return success;

    }

    void insertWordVert(String word, int index) { // inserts words vertically
        if (!word.equals("")) {
            String[] chars = word.split("(?!^)");
            for (int i = 0; i < getDimension(); i++) {
                this.grid[i][index].setOutput(chars[i]);
            }
        }
    }

    void insertWordHoriz(String word, int index) { // inserts words horizontally
        if (!word.equals("")) {
            String[] chars = word.split("(?!^)");
            for (int i = 0; i < getDimension(); i++) {
                this.grid[index][i].setOutput(chars[i]);
            }
        }
    }

    public void removeWord(int index) { // removes a word
        for (int j = 0; j < getDimension(); j++) {
            this.grid[j][index] = new Cell();
        }
    }

    private void clearGrid() { // wipes the grid completely
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void removeWordUnder(int index) { // removes the word under the top row. this prevents the remove word
                                              // method from disrupting the initial grid.
        for (int i = 1; i < getDimension(); i++) {
            grid[i][index] = new Cell();
        }
    }

    private void initializeCells(Cell[][] grid) { // initializes each cell in the Cell[][]
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public boolean isComplete() { // checks if every cell in the grid has some nonempty string
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                if (this.grid[i][j].getOutput().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCorrect() { // a good grid is one that is both correct and complete
        boolean success = false;
        if (this.checkGrid() && this.isComplete()) {
            success = true;
        }
        return success;
    }

    public ArrayList<String> possibleWords(int index, ArrayList<String> wordbank) { // uses regex to create a wordbank
                                                                                    // of every possible word for a row
                                                                                    // at some index
        String start = "";
        for (int i = 0; i < getDimension(); i++) {
            if (this.grid[index][i].getOutput().equals("")) {
                start = start + " ";
            } else {
                start = start + this.grid[index][i].getOutput();
            }
        }
        String regex = regex(start);
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> goodWords = new ArrayList<String>();

        for (int i = 0; i < wordbank.size(); i++) {
            Matcher m = pattern.matcher(wordbank.get(i));
            if (m.find()) {
                goodWords.add(wordbank.get(i));

            }
        }
        return goodWords;
    }

    private String regex(String word) { // creates a regex pattern using wildcards and already inputted Strings
        String regex = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ') {
                regex = regex + ".";
            } else {
                regex = regex + word.charAt(i);
            }
        }
        return regex;
    }

    public void createInitialGrid() { // creates the initial grid for the wordinput method
        this.clearGrid();
        String vert = chooseWord(wordbank);
        this.insertWordVert(vert, 0);
        String horiz = chooseWord(possibleWords(0, wordbank));
        this.insertWordHoriz(horiz, 0);

        // this.insertWordVert(maxMid, (int) getDimension() / 2);
    }

    public ArrayList<String> possibleWordsVert(int index, ArrayList<String> wordbank) { // same as possible words but
                                                                                        // for columns.
        String start = "";
        for (int i = 0; i < getDimension(); i++) {
            if (this.grid[i][index].getOutput().equals("")) {
                start = start + " ";
            } else {
                start = start + this.grid[i][index].getOutput();
            }
        }
        String regex = regex(start);
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> goodWords = new ArrayList<String>();

        for (int i = 0; i < wordbank.size(); i++) {
            Matcher m = pattern.matcher(wordbank.get(i));
            if (m.find()) {
                goodWords.add(wordbank.get(i));
            }
        }
        return goodWords;
    }

    public void printPossibles() { // prints every possible word bank. used for testing
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < possibleWords(i, wordbank).size(); j++) {
                System.out.print(this.possibleWords(i, wordbank).get(j));
                System.out.print(" ");
            }
            System.out.println();

        }
    }

    public String getCell(int x, int y) { // returns a cell
        return this.grid[x][y].getOutput();

    }

    public static String chooseWord(ArrayList<String> wordbank) { // chooses a word randomly from some word bank
        Random rand = new Random();
        try {
            int index = rand.nextInt(wordbank.size());
            return wordbank.get(index);
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    public boolean matches(GeneratedGrid someGrid) { // used to see if a grid matches another grid. the standard Java
                                                     // .equals(Object o) method was insufficient.
        boolean success = true;
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                if (!this.grid[i][j].getOutput().equals(someGrid.grid[i][j].getOutput())) {
                    success = false;
                }
            }
        }
        return success;
    }

    private static ArrayList<String> wordScanner(String filename, int N) { // scans the initial word bank text file.
        ArrayList<String> wordbank = new ArrayList<String>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                word = word.toLowerCase();
                if (word.length() == N && isClean(word)) {

                    wordbank.add(word);

                }
            }
        } catch (FileNotFoundException f) {
            System.out.println("File not found");
        }

        return wordbank;
    }

    public void addWord(int n) { // chooses a word from the i-th most vulnerable across or down. Most vulnerable
                                 // means the smallest possible word bank.
        int[] sizes = new int[getDimension() * 2];
        for (int i = 0; i < getDimension(); i++) {
            sizes[i] = this.possibleWordsVert(i, wordbank).size();
            sizes[i + getDimension()] = this.possibleWords(i, wordbank).size();
        }
        for (int i = 0; i < getDimension() * 2; i++) {
            if (sizes[i] == 1) {
                sizes[i] = 5000; // some large number
            }
        }
        int temp;
        for (int j = 0; j < sizes.length; j++) {
            for (int k = j + 1; k < sizes.length; k++) {
                if (sizes[j] > sizes[k]) {
                    temp = sizes[j];
                    sizes[j] = sizes[k];
                    sizes[k] = temp;
                }
            }
        }

        String word = "";
        int pos = 0;
        boolean vert = true;
        for (int i = 0; i < getDimension(); i++) {
            if (sizes[n] == this.possibleWordsVert(i, wordbank).size()) {

                pos = i;
                vert = true;

                break;
            } else if (sizes[n] == this.possibleWords(i, wordbank).size()) {

                pos = i;
                vert = false;
                break;
            }
        }
        try {

            if (vert) {
                // if (this.grid[0][pos].getOutput().equals("")) {
                word = chooseWord(this.possibleWordsVert(pos, wordbank));
                this.insertWordVert(word, pos);
                // }
            } else {
                // if (this.grid[pos][0].getOutput().equals("")) {
                word = chooseWord(this.possibleWords(pos, wordbank));
                this.insertWordHoriz(word, pos);
                // }
            }
        } catch (NullPointerException e) {
        }

    }

    public ArrayList<String> getWordBank() { // returns the wordbank
        return wordbank;
    }

    public static int getDimension() { // returns the dimension
        return N;
    }

    public static String getLang() { // returns the language
        return lang;
    }

    private static boolean isClean(String string) { // determines if a string contains only ascii characters
        for (char c : string.toCharArray()) {
            if ((int) c > 122 || (int) c < 97) {
                return false;
            }
        }
        return true;
    }
}

class DepthFirstSearch extends GeneratedGrid { // uses DFS to compute a correct crossword
    Node startNode;

    public DepthFirstSearch(Node start) {
        super(getDimension(), GeneratedGrid.getLang());
        this.startNode = start;

    }

    public GeneratedGrid DFS() { // Uses depth first search. runs until it finds a correct puzzle.
        GeneratedGrid output = new GeneratedGrid(getDimension(), getLang());
        if (this.startNode.isCorrect) {
        }
        Stack<Node> nodeStack = new Stack<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();

        nodeStack.push(startNode);

        OUTER_LOOP: while (!nodeStack.isEmpty()) {
            Node current = nodeStack.pop();
            if (Node.contains(visited, current) && !current.isCorrect) {
                current = nodeStack.pop();
            }

            PrintGrid.noGraphicsPrint(current.grid);
            System.out.println();

            if (current.grid.checkGrid() && current.grid.isComplete()) {
                return current.grid;
            } else {
                visited.add(current);
                ArrayList<Node> next = new ArrayList<Node>();

                for (int i = 0; i < getDimension() * 2; i++) {
                    int count = 0;
                    while (count < 7) {
                        Node temp = new Node(current);
                        temp.grid.addWord(i);
                        if (temp.checkGrid == false) {
                            visited.add(temp);

                        }
                        count++;
                        if (temp.isCorrect) {
                            break OUTER_LOOP;
                        }
                        if (Node.contains(visited, temp) == false) {
                            next.add(0, temp);
                        }
                    }
                }

                nodeStack.addAll(next);
            }
        }
        return output;
    }
}

class Node {
    GeneratedGrid grid;
    boolean isCorrect;
    boolean checkGrid;

    public Node(GeneratedGrid grid) {
        this.grid = grid;
        isCorrect = grid.isCorrect();
        checkGrid = grid.checkGrid();

    }

    public Node(Node node) {
        this.isCorrect = node.isCorrect;
        int n = GeneratedGrid.getDimension();
        GeneratedGrid clonedGrid = new GeneratedGrid(n, GeneratedGrid.getLang());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clonedGrid.grid[i][j].setOutput(node.grid.grid[i][j].getOutput());
            }
        }
        this.grid = clonedGrid;
        this.checkGrid = clonedGrid.checkGrid();
    }

    public static boolean contains(ArrayList<Node> nodes, Node node) {
        boolean value = false;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).grid.matches(node.grid)) {
                value = true;
            }
        }

        return value;

    }
}
