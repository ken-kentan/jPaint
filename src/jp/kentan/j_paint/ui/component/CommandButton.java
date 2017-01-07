package jp.kentan.j_paint.ui.component;

import javax.swing.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class CommandButton extends JButton {
    public enum CMD{
        UNDO, REDO
    }

    private CMD cmd;

    public CommandButton(ImageIcon icon, CMD cmd){
        super(icon);
        this.cmd = cmd;

        System.out.println("CommandButton(" + cmd + ") create.");
    }


    public CMD get(){
        return cmd;
    }
}
