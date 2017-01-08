package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.ui.component.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;


class UIEventListener implements ActionListener, ChangeListener, MouseListener, ItemListener, DocumentListener {
    private UIController ui;
    private JPaintController controller;

    private volatile boolean isThreadRunning = false;

    UIEventListener(JPaintController controller, UIController ui){
        this.controller = controller;
        this.ui = ui;

        System.out.println("UIEventListener create.");
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj instanceof ToolButton){
            ToolButton button = (ToolButton)obj;

            controller.setLayerTool(button.get());
        }else if(obj instanceof CommandButton){
            CommandButton button = (CommandButton)obj;

            if(button.get() == CommandButton.CMD.UNDO){
                controller.undo();
            }else{
                controller.redo();
            }
        }else if(obj instanceof JTextField){
            JTextField textField = (JTextField)obj;

            textField.setFocusable(false);
            textField.setFocusable(true);

            if(textField.getName().equals("ToolSize")){
                controller.setLayerToolSize(textField.getText());
            }
        }else if(obj instanceof CornerRadioButton){
            CornerRadioButton button = (CornerRadioButton)obj;
            controller.setLayerToolCornerType(button.get());
        }else if(obj instanceof BrushRadioButton){
            BrushRadioButton button = (BrushRadioButton)obj;
            controller.setLayerToolBrushType(button.get());
        }else if(obj instanceof JCheckBox){
            JCheckBox checkBox = (JCheckBox)obj;

            if(checkBox.getName().equals("TextBrushOption")){
                controller.setLayerToolTextBrush(checkBox.isSelected());
            }
        }else if(obj instanceof JMenuItem){
            JMenuItem item = (JMenuItem)obj;

            switch (item.getActionCommand()){
                case "New":
                    String[] strSize = Dialog.showNewCanvasDialog();

                    if(strSize != null) controller.createNewCanvas(strSize);
                    break;
                case "Exit":
                    System.out.println("Bye bye.");
                    System.exit(0);
                    break;
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        Object obj = e.getSource();

        if(e.getStateChange() != ItemEvent.SELECTED) return;

        if(obj instanceof FontsComboBox){
            FontsComboBox fontsComboBox = (FontsComboBox)obj;

            controller.setLayerToolFont(fontsComboBox.getSelectedFont());
        }else if(obj instanceof JComboBox){
            JComboBox comboBox = (JComboBox)obj;

            comboBox.setFocusable(false);
            comboBox.setFocusable(true);

            controller.setLayerFontSize(comboBox.getSelectedItem().toString());
        }
    }

    public void stateChanged(ChangeEvent e){
        Object obj = e.getSource();

        if(obj instanceof JSlider){
            JSlider slider = (JSlider)obj;

            controller.setLayerToolSize(slider.getValue());
        }
    }

    public void changedUpdate(DocumentEvent e) {
    }

    public void removeUpdate(DocumentEvent e) {
        Document doc = e.getDocument();

        SwingUtilities.invokeLater(() -> {
            try {
                controller.setLayerToolText(doc.getText(0 , doc.getLength()));
            }catch (Exception exc){
                Dialog.showWarningMsg("入力を処理できませんでした。\nエラー: " + exc.getMessage());
            }
        });
    }

    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();

        SwingUtilities.invokeLater(() -> {
            try {
                controller.setLayerToolText(doc.getText(0 , doc.getLength()));
            }catch (Exception exc){
                Dialog.showWarningMsg("入力を処理できませんでした。\nエラー: " + exc.getMessage());
            }
        });
    }

    public void mouseClicked(MouseEvent e){
        Object obj = e.getSource();

        if(obj instanceof JPanel){
            JPanel panel = (JPanel)obj;
            Color color = Dialog.showColorChooser(panel.getBackground());

            if(color != null){
                panel.setBackground(color);
                controller.setLayerToolColor(color);
            }
        }
    }

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){
    }

    public void mousePressed(MouseEvent e){
    }

    public void mouseReleased(MouseEvent e){
    }
}
