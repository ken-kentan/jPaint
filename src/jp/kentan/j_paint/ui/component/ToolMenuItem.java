package jp.kentan.j_paint.ui.component;

import jp.kentan.j_paint.tool.Tool;

import javax.swing.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class ToolMenuItem extends JMenuItem {
    private Tool.TYPE type;

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
