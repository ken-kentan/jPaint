package jp.kentan.j_paint.ui.component;

import javax.swing.*;


public class CommandMenuItem extends JMenuItem {
    public enum CMD{
        UNDO, REDO
    }

    private final CMD cmd;

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
