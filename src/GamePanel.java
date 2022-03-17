import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{
    Thread thread;
    Textbox[][] grid;
    String word;
    Textbox focusedTextbox;
    int focusedX, focusedY, chemicalLength;
    ChemicalMaps cm;
    KeyStroke backspace;

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
        setPreferredSize(new Dimension(chemicalLength*100,600));
        grid = new Textbox[chemicalLength][6];
        for(int x=0; x<chemicalLength; x++) {
            for (int y=0; y<6; y++) {
                grid[x][y] = new Textbox(x,y);
                add(grid[x][y]);
            }
        }
        focusedTextbox = grid[0][0];
        focusedTextbox.requestFocus();
        backspace = KeyStroke.getKeyStroke((char) KeyEvent.VK_BACK_SPACE);
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void generateChemical() {

    }

    public void testLine(int line) {
        int pos = 0;
        if (Objects.equals(grid[pos][line].getText(), cm.cation)) {
            grid[pos][line].setBackground(Color.GREEN);
        } else {
            grid[pos][line].setBackground(Color.LIGHT_GRAY);
        }
        pos++;
        if (isNumber(grid[pos][line].getText())) {
            if (Integer.parseInt(grid[pos][line].getText()) == -cm.anionCharge) {
                grid[pos][line].setBackground(Color.GREEN);
            } else {
                grid[pos][line].setBackground(Color.LIGHT_GRAY);
            }
            pos++;
        }
        if (Objects.equals(grid[pos][line].getText(), cm.anion)) {
            grid[pos][line].setBackground(Color.GREEN);
        } else {
            grid[pos][line].setBackground(Color.LIGHT_GRAY);
        }
        pos++;
        if (pos != grid.length && isNumber(grid[pos][line].getText())) {
            if (Integer.parseInt(grid[pos][line].getText()) == cm.cationCharge) {
                grid[pos][line].setBackground(Color.GREEN);
            } else {
                grid[pos][line].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public Object detectKey() {
        System.out.println("detecting");
        for (int x=0; x<5; x++) {
            for (int y=0; y<6; y++) {
                if (grid[x][y].hasFocus()) {
                    grid[x+1][y].requestFocus();
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        while(thread != null) {
            for (int x=0; x<chemicalLength; x++) {
                for (int y=0; y<6; y++) {
                    if (grid[x][y].isFocusOwner()) {
                        focusedTextbox = grid[x][y];
                    }
                }
            }
            focusedX = focusedTextbox.getX()/100;
            focusedY = focusedTextbox.getY()/100;
            if (focusedTextbox != null) {
                if (cm.cationMap.containsKey(focusedTextbox.getText()) || cm.anionMap.containsKey(focusedTextbox.getText()) || isNumber(focusedTextbox.getText())) {
                    if (focusedX < chemicalLength-1) {
                        grid[focusedX+1][focusedY].requestFocus();
                    } else {
                        testLine(focusedY);
                        grid[0][focusedY+1].requestFocus();
                    }
                }
            }
            focusedTextbox.getKeymap().addActionForKeyStroke(backspace, new NextBoxAction());
            if (focusedTextbox.getText().contains("2")) {
                focusedTextbox.setText(focusedTextbox.getText().replace("2","\u2082"));
            }
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
}
