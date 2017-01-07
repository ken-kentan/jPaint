package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


class Window extends JFrame implements WindowListener {
    Canvas canvas;
    OptionBar optionBar;

    Window(UIController controller, UIEventListener listener){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setTitle("jPaint");

        this.setLayout(new BorderLayout());

        this.setJMenuBar(new MenuBar());

        this.add(optionBar = new OptionBar(listener), BorderLayout.NORTH);
        this.add(new SideBar(controller, listener), BorderLayout.WEST);
        this.add(new InfoBar(), BorderLayout.SOUTH);

        this.add(canvas = new Canvas(), BorderLayout.CENTER);
    }


    /*
    Windowイベント
     */
    public void windowOpened(WindowEvent e){
    }

    public void windowClosing(WindowEvent e){
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
}
