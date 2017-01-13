package jp.kentan.j_paint.ui.component;

import javax.swing.*;


public class CommandButton extends JButton {
    public enum CMD{
        UNDO, REDO
    }

    private final CMD cmd;

    public CommandButton(ImageIcon icon, CMD cmd){
        super(icon);
        this.cmd = cmd;

        System.out.println("CommandButton(" + cmd + ") create.");
    }


    public CMD get(){
        return cmd;
    }
}
