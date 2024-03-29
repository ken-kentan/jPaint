package jp.kentan.j_paint.tool;


import jp.kentan.j_paint.ui.component.FontsComboBox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tool {
    public enum TYPE{
        LINE, RECT, OVAL, TEXT, PEN, BRUSH, DROPPER,  ERASER
    }

    private TYPE type;
    Color color = Color.BLACK;
    Color colorDash = Color.BLACK;
    Stroke stroke = new BasicStroke();
    Stroke strokeDash = new BasicStroke();
    Font font = FontsComboBox.getDefaultFont();
    String text = "Text";
    BufferedImage image;
    boolean isShape = true;  //角形状
    boolean isFill = false;  //塗りつぶし
    boolean isCircle = true; //Brush, Eraser形状
    boolean isStamp = true;  //Textをスタンプ化
    int size = 1;

    Tool(TYPE type){
        this.type = type;

        System.out.println("Tool(" + type + ") create.");
    }

    void update(){
        if(isShape && (type != TYPE.PEN && type != TYPE.BRUSH && type != TYPE.ERASER)){
            stroke = new BasicStroke(size, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 100.0f);

        }else{
            if(isCircle){
                stroke = new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0);
            }else{
                stroke = new BasicStroke(size);
            }
        }

        strokeDash = new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 100.0f, new float[]{size*3}, 0);
        colorDash = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    }

    TYPE get(){
        return this.type;
    }
}
