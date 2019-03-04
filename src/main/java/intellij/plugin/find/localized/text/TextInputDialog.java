package intellij.plugin.find.localized.text;

import javax.swing.*;
import java.awt.event.*;

public class TextInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public boolean isOk() {
        return ok;
    }

    private boolean ok = false;

    public String getText() {
        return text.getText();
    }

    private JTextField text;

    public TextInputDialog(String clipboard) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Find Localized Text Key");

        getRootPane().setDefaultButton(buttonOK);

        text.setText(clipboard);
        text.selectAll();
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        ok = true;
        dispose();
    }

    private void onCancel() {
        dispose();

    }


}
