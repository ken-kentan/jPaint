package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.layer.LayerController;

import javax.swing.*;
import java.awt.*;


class Canvas extends JPanel {
    private static final Dimension DEFAULT_SIZE = new Dimension(500, 500);

    Canvas(){
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());

//        JPanel test = new JPanel();
//        test.setPreferredSize(DEFAULT_SIZE);
//        test.setMinimumSize(DEFAULT_SIZE);

        System.out.println("Canvas create.");
    }

    void setLayer(LayerController layer){
        this.add(layer);

        validate();
        repaint();
    }
}
