public class PrintGrid {// extends JPanel { // this will later include graphical printing
    public static void noGraphicsPrint(GeneratedGrid genGrid) {

        for (int i = 0; i < genGrid.getDimension(); i++) {
            if (i == 0) {
                for (int j = 0; j < genGrid.getDimension(); j++) {

                    System.out.printf("%d ", j);

                }
                System.out.println();
            }

            for (int k = 0; k < genGrid.getDimension(); k++) {
                String someLetter = genGrid.grid[i][k].getOutput();
                someLetter = someLetter.toUpperCase();
                System.out.printf("%s ", someLetter);
            }
            System.out.println();
        }
    }
}
