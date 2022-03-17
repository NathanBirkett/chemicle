import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

public class Textbox extends JTextPane{
    Font font;
    public Textbox(int x, int y) {
        font = new Font(Font.MONOSPACED, Font.PLAIN,50);
        setBounds(x*100+5,y*100+5,90,90);
        setFont(font);
    }
}
