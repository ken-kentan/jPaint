package jp.kentan.j_paint.ui.component;

import javax.swing.*;


public class CornerRadioButton extends JRadioButton {
    public enum TYPE{
        SHARP, ROUND
    }

    private TYPE type;

    public CornerRadioButton(TYPE type){
        this.type = type;

        System.out.println("CornerRadioButton(" + type + ") create.");
    }

    public TYPE get(){
        return type;
    }
}
