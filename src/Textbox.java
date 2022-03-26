import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

public class Textbox extends JTextPane{
    Font font;
    public Textbox(int y) {
        font = new Font(Font.MONOSPACED, Font.PLAIN,50);
        setBounds(10,y*100+10,500,90);
        setFont(font);
    }
}
