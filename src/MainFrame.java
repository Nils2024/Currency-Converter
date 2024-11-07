import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.simple.parser.ParseException;

public class MainFrame extends JFrame{

    InsertMoneyTextArea insertMoneyTextArea = new InsertMoneyTextArea();
    SelectCurrencyBox currentCurrencyBox = new SelectCurrencyBox();
    SelectCurrencyBox changeCurrencyBox = new SelectCurrencyBox();
    ChangedCurrency changedCurrencyResult = new ChangedCurrency();

    API api = new API();

    JPanel insertPanel;
    JPanel resultPanel;

    public MainFrame() throws IOException, ParseException {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        insertPanel = new JPanel();
        resultPanel = new JPanel();
        this.setResizable(false);
        contentPane.setLayout(new GridLayout(2,1));

        insertPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100,80));
        //insertPanel.setBackground(Color.green);

        resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
        //resultPanel.setBackground(Color.gray);

        //Setzt Größe des Eingabefeldes
        insertMoneyTextArea.setText("");
        insertMoneyTextArea.setSize(new Dimension(700,100));
        insertMoneyTextArea.setFont(new Font("xy", Font.BOLD, 30));
        insertMoneyTextArea.setBackground(Color.black);
        insertMoneyTextArea.setForeground(Color.green);
        insertMoneyTextArea.setToolTipText("Geben Sie einen Wert ein");
        insertMoneyTextArea.setColumns(7);

        //Setzt Größe des Ergebnisfeldes
        changedCurrencyResult.setEditable(false);
        changedCurrencyResult.setText("");
        changedCurrencyResult.setSize(new Dimension(700,100));
        changedCurrencyResult.setFont(new Font("xy", Font.BOLD, 30));
        changedCurrencyResult.setBackground(Color.black);
        changedCurrencyResult.setForeground(Color.green);
        changedCurrencyResult.setToolTipText("Changed Currency");
        changedCurrencyResult.setColumns(7);

        //Allgemeine Frame Einstellungen
        this.setBounds(800,500,500,500);
        this.setLocationRelativeTo(null);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Panel add
        insertPanel.add(currentCurrencyBox);
        insertPanel.add(insertMoneyTextArea);
        resultPanel.add(changeCurrencyBox);
        resultPanel.add(changedCurrencyResult);
        contentPane.add(insertPanel);
        contentPane.add(resultPanel);
        this.setVisible(true);

        api.fetchApi();
        api.addCurrencyNames();
        setCurrencyNames();

        changeCurrencyBox.addActionListener(changeCurrencyBoxClicked);
        currentCurrencyBox.addActionListener(changeCurrencyBoxClicked);

        insertMoneyTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (insertMoneyTextArea.getText().isEmpty() ){
                    changedCurrencyResult.setText("");
                }
                loop();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (insertMoneyTextArea.getText().isEmpty() ){
                    changedCurrencyResult.setText("");
                }
                loop();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (insertMoneyTextArea.getText().isEmpty() ){
                    changedCurrencyResult.setText("");
                }
                loop();
            }
        });

    }

    public void setCurrencyNames(){
        for (String currName : api.getCurrencyNames()){
            currentCurrencyBox.addItem(currName);
            changeCurrencyBox.addItem(currName);
        }
    }

    public double calculate(double enteredNumber){
        double inUSD = 1.0;

        String currentCurrency = (String) currentCurrencyBox.getSelectedItem();
        double valueCurrentCurrency = (double) api.getCurrencyRate(currentCurrency);

        String changeCurrency = (String) changeCurrencyBox.getSelectedItem();
        double valueChangeCurrency = api.getCurrencyRate(changeCurrency);

        double mulitplicatorInUSD = inUSD / valueCurrentCurrency;
        double resultInUSD = mulitplicatorInUSD * enteredNumber;
        double result = resultInUSD * valueChangeCurrency;

        if (result > 1 ){
            BigDecimal bd = new BigDecimal(Double.toString(result));
            bd = bd.setScale(3, RoundingMode.HALF_UP);

            return bd.doubleValue();
        }
        else {
            BigDecimal bd = new BigDecimal(Double.toString(result));
            bd = bd.setScale(8, RoundingMode.HALF_UP);

            return bd.doubleValue();
        }
    }

    public void loop(){
        if (!insertMoneyTextArea.getText().isEmpty()){
            double enteredValue = Double.parseDouble(insertMoneyTextArea.getText());
            double result = calculate(enteredValue);
            String stringResult = Double.toString(result);
            changedCurrencyResult.setText(stringResult);
        }
    }

    ActionListener changeCurrencyBoxClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loop();
        }
    };





}
