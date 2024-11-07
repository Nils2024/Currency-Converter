import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectCurrencyBox extends JComboBox {

    String currencySelected;

    public SelectCurrencyBox(){
        this.addActionListener(boxActionListener);
    }

    ActionListener boxActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currencySelected = getSelectedItem().toString();
        }
    };

    //Don't use, sends Information too slow, use getSelectedItem()
    public String getCurrencySelected(){
        return currencySelected;
    }



}
