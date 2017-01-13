package jp.kentan.j_paint.ui.component;

import jp.kentan.j_paint.tool.Tool;

import javax.swing.*;


public class ToolMenuItem extends JMenuItem {
    private final Tool.TYPE type;

    public ToolMenuItem(String name, Tool.TYPE type){
        super(name);
        this.type = type;
        this.setActionCommand("ToolSelect");

        System.out.println("ToolMenuItem(" + type + ") create.");
    }

    public Tool.TYPE get(){
        return type;
    }
}
