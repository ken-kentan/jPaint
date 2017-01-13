package jp.kentan.j_paint.ui.component;

import jp.kentan.j_paint.tool.Tool;

import javax.swing.*;


public class ToolButton extends JButton {
    private final Tool.TYPE type;

    public ToolButton(ImageIcon icon, Tool.TYPE type){
        super(icon);
        this.type = type;

        System.out.println("ToolButton(" + type + ") create.");
    }

    public Tool.TYPE get(){
        return type;
    }
}
