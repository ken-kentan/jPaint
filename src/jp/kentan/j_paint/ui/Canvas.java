package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.layer.LayerController;

import javax.swing.*;
import java.awt.*;


class Canvas extends JPanel {
    private LayerController layer = null;

    Canvas(){
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());

        System.out.println("Canvas create.");
    }

    void setLayer(LayerController layer){
        if(this.layer != null){
            this.remove(this.layer);
            System.out.println("Previous LayerController removed.");
        }

        this.add(this.layer = layer);

        validate();
        repaint();
    }
}
