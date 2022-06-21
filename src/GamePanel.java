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
    ArrayList<String> entryTokens;
    int lineNumber;

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
        setPreferredSize(new Dimension(520,10*100));
        grid = new Textbox[10];
            for (int y=0; y<10; y++) {
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
        focusedTextbox.setText(focusedTextbox.getText().substring(0,focusedTextbox.getText().length()-2));
        entryTokens = cm.dissect(focusedTextbox.getText());
        if(!isValid(entryTokens)) {
            System.out.println("INCORRECT ENTRY");
            return;
        }
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
                        // e.printStackTrace();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("out of bounds");
            }
        }
        grid[focusedY+1].requestFocus();
        lineNumber++;
    }

    public void dissect(String str) {
        entryTokens = new ArrayList<>();
        String chemical = str;
        for (int i=0; i<chemical.length(); i++) {
            char chr = chemical.charAt(i); 
            if (chr == '(') {
                if (Character.isUpperCase(chemical.charAt(i-1))) {
                    entryTokens.add(""+chemical.charAt(i-1));
                    System.out.println(entryTokens);
                }
                if (i != 0 && !Character.isDigit(chemical.charAt(i-1))) {
                    entryTokens.add("1");
                    System.out.println(entryTokens);
                }
                entryTokens.add(chemical.substring(i+1, chemical.indexOf(')', i)));
                System.out.println(entryTokens);
                i = chemical.indexOf(')', i)-1;
            } 
            if (i != 0 && Character.isUpperCase(chr) && !Character.isDigit(chemical.charAt(i-1))) {
                entryTokens.add("1");
                System.out.println(entryTokens);
            }
            if (Character.isLowerCase(chr)) {
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
        if (!Character.isDigit(chemical.charAt(chemical.length()-3))) {
            entryTokens.add("1");
            System.out.println(entryTokens);
        }
        System.out.println(entryTokens);
    }

    public boolean isValid(ArrayList<String> tokens) {
        return cm.isValid(tokens);
    }

    @Override
    public void run() {
        while(thread != null) {
            grid[lineNumber].requestFocus();
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
            testLine(focusedY);
        }
    }
}
