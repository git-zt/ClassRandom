import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame jf = new JFrame("1904课堂点名");
        jf.setBounds(10,10,900,620);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(new Spanel());

        jf.setVisible(true);
    }
}
