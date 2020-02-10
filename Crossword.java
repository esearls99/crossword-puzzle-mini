public class Crossword {
    // String flag; used to generate categories. Might be used in with more
    // developed wordbank.

    public static void main(String[] args) {
        if (args.length != 4) {
            showUsageAndExit();
        } else {
            try {
                String lang = args[1];
                int n = Integer.parseInt(args[0]);

                long startTime = System.nanoTime();
                GeneratedGrid genGrid = new GeneratedGrid(n, lang);

                if (args[3].equals("brutal")) {
                    genGrid.wordInputWithInit();

                } else if (args[3].equals("DFS")) {
                    Node start = new Node(genGrid);
                    DepthFirstSearch input = new DepthFirstSearch(start);
                    genGrid = input.DFS();
                } else {
                    showUsageAndExit();
                }

                if (args[2].equals("console")) {

                    PrintGrid.noGraphicsPrint(genGrid);
                } else if (args[2].equals("graphics")) {
                    Visual frame = new Visual(genGrid);
                    frame.display();
                } else {
                    showUsageAndExit();
                }

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println(totalTime / (1000000000.00));
            }

            catch (NumberFormatException e) {
                showUsageAndExit();
            }
        }

    }

    public static void showUsageAndExit() {
        System.out.println("Enter something of the form:");
        System.out.println("java Crossword <size> <word list> <graphics mode> <generation mode>");
        System.out.println("[3 | 4]");
        System.out.println("[standard | names | acronyms]");
        System.out.println("[console | graphics]");
        System.out.println("[brutal | DFS]");
        System.exit(1);
    }
}
