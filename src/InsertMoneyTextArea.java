import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class InsertMoneyTextArea extends JTextField {

    public InsertMoneyTextArea(){
    }

    public void processKeyEvent(KeyEvent ev){

        char c = ev.getKeyChar();
        try {
            if (c > 31 && c < 127){
                Integer.parseInt(c + "");
            }
            super.processKeyEvent(ev);
        }
        catch (NumberFormatException nfe){
        }
    }



}
