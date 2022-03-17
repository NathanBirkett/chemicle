import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();
        panel.startThread();
    }
}
