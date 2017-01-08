package jp.kentan.j_paint.ui.component;

import javax.swing.*;


public class BrushRadioButton extends JRadioButton {
    public enum TYPE{
        CIRCLE, SQUARE
    }

    private TYPE type;

    public BrushRadioButton(TYPE type){
        this.type = type;

        System.out.println("BrushRadioButton(" + type + ") create.");
    }

    public TYPE get(){
        return type;
    }
}
