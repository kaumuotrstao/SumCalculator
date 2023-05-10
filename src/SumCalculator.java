import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SumCalculator extends JFrame {
    private JTextField[] textFields = new JTextField[8];
    private JLabel[] titles = new JLabel[8];
    private JLabel resultLabel;
    private JLabel budgetLabel;
    private JTextField budgetField;
    private JLabel budgetStatusLabel;

    public SumCalculator() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
     // Page title label
        JLabel titleLabel = new JLabel("【自作PC予算シミュレーター】");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        
     // Create vertical space
        add(Box.createVerticalStrut(25)); 

        String[] names = {"CPU", "グラボ", "CPUクーラー", "マザボ", "メモリ", "SSD", "ケース", "電源"};

     // Budget field and label
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.X_AXIS));
        budgetPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        budgetLabel = new JLabel("設定予算:");
        budgetField = new JTextField(5);
        budgetField.setMaximumSize(budgetField.getPreferredSize());
        budgetField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }
            public void removeUpdate(DocumentEvent e) {
                calculate();
            }
            public void insertUpdate(DocumentEvent e) {
                calculate();
            }
        });

        JLabel yenLabelBudget = new JLabel("円");

        budgetPanel.add(Box.createHorizontalGlue()); 
        budgetPanel.add(budgetLabel);
        budgetPanel.add(budgetField);
        budgetPanel.add(yenLabelBudget); 
        budgetPanel.add(Box.createHorizontalGlue()); 

        add(budgetPanel);



        for (int i = 0; i < textFields.length; i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            titles[i] = new JLabel(names[i] + ":");
            panel.add(titles[i]);

            textFields[i] = new JTextField(5);
            textFields[i].getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    calculate();
                }
                public void removeUpdate(DocumentEvent e) {
                    calculate();
                }
                public void insertUpdate(DocumentEvent e) {
                    calculate();
                }
            });

            JLabel yenLabel = new JLabel("円");

            // Enter key action
            textFields[i].getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "moveToNextComponent");
            textFields[i].getActionMap().put("moveToNextComponent", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusNextComponent();
                }
            });

            panel.add(textFields[i]);
            panel.add(yenLabel); 

            add(panel); 
        }


        resultLabel = new JLabel("結果: 0円");
        add(resultLabel);

        // Add budget status label
        budgetStatusLabel = new JLabel("");
        add(budgetStatusLabel);

        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void calculate() {
        int sum = 0;
        for (JTextField textField : textFields) {
            try {
                sum += Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                // Ignore if text field does not contain a valid integer
            }
        }
        resultLabel.setText("結果: " + sum + "円");

        // Check if sum exceeds budget
        try {
            int budget = Integer.parseInt(budgetField.getText());
            if (sum > budget) {
                budgetStatusLabel.setText("予算オーバーです");
            } else {
                budgetStatusLabel.setText("予算以内です");
            }
        } catch (NumberFormatException e) {
            // Ignore if budget field does not contain a valid integer

            }
        }

    public static void main(String[] args) {
        new SumCalculator();
    }
}
