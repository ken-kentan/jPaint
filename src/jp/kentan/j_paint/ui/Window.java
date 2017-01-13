package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.*;

class Window extends JFrame {
    Canvas canvas;
    OptionBar option;
    InfoBar info;

    Window(UIController controller, UIEventListener listener){
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setTitle("jPaint");

        this.setLayout(new BorderLayout());

        this.setJMenuBar(new MenuBar(controller, listener));

        this.add(option = new OptionBar(listener), BorderLayout.NORTH);
        this.add(new SideBar(controller, listener), BorderLayout.WEST);
        this.add(info = new InfoBar(), BorderLayout.SOUTH);

        this.add(canvas = new Canvas(), BorderLayout.CENTER);

        this.addWindowListener(listener);
    }
}
