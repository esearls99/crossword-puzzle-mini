import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;

public class Visual extends JPanel {

    //private DrawPanel[][] panels
    private GeneratedGrid genGrid;
    private static final int size = 75;
    public Visual(GeneratedGrid gGrid) {
        genGrid = gGrid;
        int dimension = GeneratedGrid.getDimension();
        setLayout(new GridLayout(dimension, dimension));
        this.setPreferredSize(new Dimension(dimension * size, dimension * size));
        
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                String word = genGrid.grid[i][j].getOutput();
                this.add(new Letter(word));

            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }


/*interface Drawable {
    void draw(Graphics g);
}*/

    private static class Letter extends JButton {

    public Letter(String input) {
        input = input.toUpperCase();
        setText(input);
        this.setFont(new Font("Arial", Font.PLAIN, 40));
        this.setOpaque(true);
        this.setBorderPainted(true);
        }

    }

    public void display() {
        JFrame f = new JFrame("ButtonBorder");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setLocation(400, 400);
        f.pack();
        f.setTitle("Crossword");
        f.setVisible(true);

    }

}
