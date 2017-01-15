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
import java.io.File;


class UIEventListener implements ActionListener, ChangeListener, MouseListener, ItemListener, DocumentListener, WindowListener {
    private JPaintController controller;

    UIEventListener(JPaintController controller){
        this.controller = controller;

        System.out.println("UIEventListener create.");
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj instanceof ToolButton){ //サイドパネルのツールボタン
            ToolButton button = (ToolButton)obj;

            controller.setLayerTool(button.get());
        }else if(obj instanceof CommandButton){ //サイドパネルのRedo,Undoボタン
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

            switch (checkBox.getActionCommand()){
                case "FillOption":
                    controller.setLayerToolFill(checkBox.isSelected());
                    break;
                case "TextBrushOption":
                    controller.setLayerToolTextBrush(checkBox.isSelected());
                    break;
            }
        }else if(obj instanceof JMenuItem){
            File file;
            JMenuItem item = (JMenuItem)obj;

            switch (item.getActionCommand()){
                case "New":
                    if(controller.isSavedCanvas()) return;

                    String[] strSize = Dialog.showNewCanvasDialog();

                    if(strSize != null) controller.createNewCanvas(strSize);
                    break;
                case "Open":
                    if(controller.isSavedCanvas()) return;

                    file = Dialog.showImageFileChooser();

                    if(file != null) controller.createNewCanvasFromImage(file);
                    break;
                case "Save":
                    if(!controller.saveCanvas(null)){ //untitledの場合,新規保存
                        file = Dialog.showSaveFileChooser();

                        if(file != null) controller.saveCanvas(file);
                    }
                    break;
                case "Save As":
                    file = Dialog.showSaveFileChooser();

                    if(file != null) controller.saveCanvas(file);
                    break;
                case "Exit":
                    controller.close();
                    break;
                case "ToolSelect":
                    ToolMenuItem itemTool = (ToolMenuItem)obj;

                    controller.setLayerTool(itemTool.get());
                    break;
                case "Command":
                    CommandMenuItem itemCommand = (CommandMenuItem)obj;

                    if(itemCommand.get() == CommandMenuItem.CMD.UNDO){
                        controller.undo();
                    }else{
                        controller.redo();
                    }
                    break;
                case "ClearAll":
                    controller.clearCanvas();
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
        final Document doc = e.getDocument();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    controller.setLayerToolText(doc.getText(0 , doc.getLength()));
                }catch (Exception exc){
                    Dialog.showWarningMsg("入力を処理できませんでした。\nエラー: " + exc.getMessage());
                }
            }
        });
    }

    public void insertUpdate(DocumentEvent e) {
        final Document doc = e.getDocument();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    controller.setLayerToolText(doc.getText(0 , doc.getLength()));
                }catch (Exception exc){
                    Dialog.showWarningMsg("入力を処理できませんでした。\nエラー: " + exc.getMessage());
                }
            }
        });
    }

    public void windowOpened(WindowEvent e){
    }

    public void windowClosing(WindowEvent e){
        controller.close();
    }

    public void windowClosed(WindowEvent e){
    }

    public void windowIconified(WindowEvent e){
    }

    public void windowDeiconified(WindowEvent e){
    }

    public void windowActivated(WindowEvent e){
    }

    public void windowDeactivated(WindowEvent e){
    }

    public void mouseClicked(MouseEvent e){
        Object obj = e.getSource();

        if(obj instanceof JPanel){
            JPanel panel = (JPanel)obj;
            Color color = Dialog.showColorChooser(panel.getBackground());

            if(color != null) controller.setLayerToolColor(color);
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
