package jp.kentan.j_paint.tool;


import javax.swing.*;
import java.awt.*;

public class Tool {
    public enum TYPE{
        LINE, RECT, OVAL, TEXT, PEN, BRUSH, ERASER
    }

    private TYPE type;
    Color color = Color.BLACK;
    Color colorDash = Color.BLACK;
    Stroke stroke = new BasicStroke();
    Stroke strokeDash = new BasicStroke();
    Font font = new JLabel().getFont();
    String text = "Text";
    boolean isShape = true;
    int size = 1;

    Tool(TYPE type){
        this.type = type;

        System.out.println("Tool(" + type + ") create.");
    }

    void update(){
        if(isShape && (type != TYPE.PEN && type != TYPE.BRUSH)){
            stroke = new BasicStroke(size, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 100.0f);

        }else{
            stroke = new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0);
        }

        strokeDash = new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 100.0f, new float[]{size*3}, 0);
        colorDash = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    }

    TYPE get(){
        return this.type;
    }
}
