package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.*;


class InfoBar extends JPanel {
    private JLabel labelInfo, labelCanvasSize;

    InfoBar(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1920, 20));
        this.setBackground(Color.DARK_GRAY);

        labelInfo = new JLabel();
        labelInfo.setForeground(Color.LIGHT_GRAY);

        labelCanvasSize = new JLabel("500px * 500px ");
        labelCanvasSize.setForeground(Color.LIGHT_GRAY);

        this.add(labelInfo, BorderLayout.WEST);
        this.add(labelCanvasSize, BorderLayout.EAST);
    }

    void setText(String text){
        labelInfo.setText(text);
    }

    void updateCanvasSize(Dimension size){
        labelCanvasSize.setText(size.width + "px * " + size.height + "px ");
    }
}
