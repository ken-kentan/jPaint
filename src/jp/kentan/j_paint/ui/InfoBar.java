package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class InfoBar extends JPanel {
    InfoBar(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(1920, 20));

        this.setBackground(Color.DARK_GRAY);
    }
}
