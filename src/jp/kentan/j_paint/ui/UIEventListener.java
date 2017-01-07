package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.ui.component.CommandButton;
import jp.kentan.j_paint.ui.component.CornerRadioButton;
import jp.kentan.j_paint.ui.component.FontsComboBox;
import jp.kentan.j_paint.ui.component.ToolButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;


class UIEventListener implements ActionListener, ChangeListener, MouseListener, ItemListener {
    private UIController ui;
    private JPaintController controller;

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

            switch (textField.getName()){
                case "ToolSize":
                    controller.setLayerToolSize(textField.getText());
                    break;
                case "InputText":
                    controller.setLayerToolText(textField.getText());
                    break;
            }
        }else if(obj instanceof CornerRadioButton){
            CornerRadioButton button = (CornerRadioButton)obj;
            controller.setLayerToolCornerType(button.get());
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
