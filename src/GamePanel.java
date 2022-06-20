import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{
    Thread thread;
    public Textbox[] grid;
    String word;
    Textbox focusedTextbox;
    int focusedX, focusedY, chemicalLength;
    ChemicalMaps cm;
    KeyStroke enter;
    char[] charArray;

    public GamePanel() {
        this.setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        cm = new ChemicalMaps();
        word = cm.generateChemical();
        for (int i=0; i<cm.chemical.length(); i++) {
            if (cm.chemical.charAt(i) >= 'A' && cm.chemical.charAt(i) <= 'Z') {
                chemicalLength++;
            } else if (isNumber(String.valueOf(cm.chemical.charAt(i)))) {
                chemicalLength++;
            }
        }
        System.out.println(chemicalLength);
        setPreferredSize(new Dimension(520,600));
        grid = new Textbox[6];
            for (int y=0; y<6; y++) {
                grid[y] = new Textbox(y);
                add(grid[y]);
            }
        focusedTextbox = grid[0];
        focusedTextbox.requestFocus();
        enter = KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER);
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void testLine(int line) {
        ArrayList<String> entryTokens = new ArrayList<>();
        String chemical = focusedTextbox.getText();
        for (int i=0; i<chemical.length(); i++) {
            char chr = chemical.charAt(i);
            if (chr == '(') {
                entryTokens.add(chemical.substring(i+1, chemical.indexOf(')', i)));
                System.out.println(entryTokens);
                i = chemical.indexOf(')', i)-1;
                continue;
            } else if (Character.isLowerCase(chr)) {
                entryTokens.add(""+chemical.charAt(i-1)+chr);
                System.out.println(entryTokens);
            } else if (Character.isDigit(chr)) {
                if (Character.isUpperCase(chemical.charAt(i-1))) {
                    entryTokens.add(""+chemical.charAt(i-1));
                    System.out.println(entryTokens);
                }
                entryTokens.add(""+chr);
                System.out.println(entryTokens);
            }
        }
        System.out.println(entryTokens);
        boolean isCorrect = true;
        Highlighter highlighter = focusedTextbox.getHighlighter();
        Highlighter.HighlightPainter greenHighlight = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
        Highlighter.HighlightPainter yellowHighlight = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        Highlighter.HighlightPainter grayHighlight = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
        System.out.println(cm.chemicalTokens);
        for (int i=0; i<entryTokens.size(); i++) {
            try {
                System.out.println(entryTokens.get(i));
                System.out.println(cm.chemicalTokens.get(i));
                if (Objects.equals(entryTokens.get(i), cm.chemicalTokens.get(i))) {
                    try {
                        highlighter.addHighlight(focusedTextbox.getText().indexOf(entryTokens.get(i)),
                                focusedTextbox.getText().indexOf(entryTokens.get(i))+entryTokens.get(i).length(),
                                greenHighlight);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("out of bounds");
            }
        }
    }

    @Override
    public void run() {
        while(thread != null) {
            for (Textbox textbox : grid) {
                if (textbox.isFocusOwner()) {
                    focusedTextbox = textbox;
                }
            }
            focusedY = focusedTextbox.getY()/100;
            focusedTextbox.getKeymap().addActionForKeyStroke(enter, new NextBoxAction());
        }
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private class NextBoxAction extends AbstractAction {
        public NextBoxAction() {

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("action performed");
            grid[focusedY+1].requestFocus();
            testLine(focusedY);
        }
    }
}
