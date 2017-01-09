package jp.kentan.j_paint.ui.component;

import javax.swing.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class CommandMenuItem extends JMenuItem {
    public enum CMD{
        UNDO, REDO
    }

    private CMD cmd;

    public CommandMenuItem(String name, CMD cmd){
        super(name);
        this.cmd = cmd;
        this.setActionCommand("Command");

        System.out.println("CommandMenuItem(" + cmd + ") create.");
    }


    public CMD get(){
        return cmd;
    }
}
