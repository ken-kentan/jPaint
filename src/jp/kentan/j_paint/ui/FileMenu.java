package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class FileMenu extends JMenu {

    FileMenu(UIEventListener listener){
        super("ファイル");

        JMenuItem menuNew, menuOpen, menuSave, menuSaveAs, menuExit;

        this.add(menuNew = new JMenuItem("新規"));
        this.add(menuOpen = new JMenuItem("開く"));
        this.addSeparator();
        this.add(menuSave = new JMenuItem("保存"));
        this.add(menuSaveAs = new JMenuItem("別名で保存"));
        this.addSeparator();
        this.add(menuExit = new JMenuItem("終了"));

        menuNew.setMnemonic(KeyEvent.VK_N);
        menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menuNew.setActionCommand("New");

        menuOpen.setMnemonic(KeyEvent.VK_O);
        menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menuOpen.setActionCommand("Open");

        menuSave.setMnemonic(KeyEvent.VK_S);
        menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menuSave.setActionCommand("Save");

        menuSaveAs.setMnemonic(KeyEvent.VK_S + KeyEvent.VK_SHIFT);
        menuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        menuSaveAs.setActionCommand("Save As");

        menuExit.setMnemonic(KeyEvent.VK_X);
        menuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        menuExit.setActionCommand("Exit");

        menuNew.addActionListener(listener);
        menuOpen.addActionListener(listener);
        menuSave.addActionListener(listener);
        menuSaveAs.addActionListener(listener);
        menuExit.addActionListener(listener);
    }

    public static String getSuffix(String fileName) {
        if (fileName == null) return null;

        int point = fileName.lastIndexOf(".");

        if (point != -1) {
            return fileName.substring(point + 1);
        }

        return null;
    }
}
