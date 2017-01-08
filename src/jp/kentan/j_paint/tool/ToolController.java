package jp.kentan.j_paint.tool;

import jp.kentan.j_paint.ui.component.BrushRadioButton;
import jp.kentan.j_paint.ui.component.CornerRadioButton;

import java.awt.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class ToolController {
    private Tool tool;
    private Tool line, rect, oval, text, pen, brush, eraser;

    public ToolController(){
        line   = new Tool(Tool.TYPE.LINE);
        rect   = new Tool(Tool.TYPE.RECT);
        oval   = new Tool(Tool.TYPE.OVAL);
        text   = new Tool(Tool.TYPE.TEXT);
        pen    = new Tool(Tool.TYPE.PEN);
        brush  = new Tool(Tool.TYPE.BRUSH);
        eraser = new Tool(Tool.TYPE.ERASER);

        //初期値
        text.size = 50;
        eraser.color = Color.WHITE;
    }

    public void set(Tool.TYPE type){
        switch (type){
            case LINE:
                tool = line;
                break;
            case RECT:
                tool = rect;
                break;
            case OVAL:
                tool = oval;
                break;
            case TEXT:
                tool = text;
                break;
            case PEN:
                tool = pen;
                break;
            case BRUSH:
                tool = brush;
                break;
            case ERASER:
                tool = eraser;
                break;
        }

        tool.update();

        System.out.println("Tool(" + type + ") set.");
    }

    public void set(Color color){
        //色は全ツール共有
        line.color = color;
        rect.color = color;
        oval.color = color;
        text.color = color;
        pen.color  = color;
        brush.color = color;

        tool.color = color;

        tool.update();

        System.out.println("Tool color(" + color + ") set.");
    }

    public void set(String str){
        text.text = str;
        tool = text;

        System.out.println("Tool text(" + str + ") set.");
    }

    public void set(Font font){
        text.font = font;
        tool = text;

        System.out.println("Tool font(" + font.getName() + ") set.");
    }

    public void set(int size){
        switch (tool.get()){
            case LINE:
                line.size = size;
                tool = line;
                break;
            case RECT:
                rect.size = size;
                tool = rect;
                break;
            case OVAL:
                oval.size = size;
                tool = oval;
                break;
            case TEXT:
                text.size = size;
                break;
            case BRUSH:
                brush.size = size;
                break;
            case ERASER:
                eraser.size = size;
                break;
        }

        tool.update();

        System.out.println("Tool size(" + size + ") set.");
    }

    public void setShape(boolean isShape){
        switch (tool.get()){
            case LINE:
                line.isShape = isShape;
                tool = line;
                break;
            case RECT:
                rect.isShape = isShape;
                tool = rect;
                break;
        }

        tool.update();

        System.out.println("Tool shape(" + isShape + ") set.");
    }

    public void setBrush(boolean isCircle){
        switch (tool.get()){
            case BRUSH:
                brush.isCircle = isCircle;
                tool = brush;
                break;
            case ERASER:
                eraser.isCircle = isCircle;
                tool = eraser;
                break;
        }

        tool.update();

        System.out.println("Tool circle(" + isCircle + ") set.");
    }

    public void setTextBrush(boolean isBrush){
        text.isStamp = !isBrush;
        tool = text;

        System.out.println("Tool stamp(" + !isBrush + ") set.");
    }

    /*
    Getter
     */
    public Tool get(){
        return tool;
    }

    public Tool.TYPE getType(){
        return tool.get();
    }

    public Color getColor(){
        return tool.color;
    }

    public Color getDashColor(){
        return tool.colorDash;
    }

    public Stroke getStroke(){
        return tool.stroke;
    }

    public Stroke getDashStroke(){
        return tool.strokeDash;
    }

    public Font getFont(){
        Font font = new Font(tool.font.getName(), Font.PLAIN, tool.size);
        return font;
    }

    public String getText(){
        return tool.text;
    }

    public int getSize(){
        return tool.size;
    }

    public CornerRadioButton.TYPE getCornerType(){
        if(tool.isShape){
            return CornerRadioButton.TYPE.SHARP;
        }else{
            return CornerRadioButton.TYPE.ROUND;
        }
    }

    public BrushRadioButton.TYPE getBrushType(){
        if(tool.isCircle){
            return BrushRadioButton.TYPE.CIRCLE;
        }else{
            return BrushRadioButton.TYPE.SQUARE;
        }
    }

    public boolean isStamp(){
        return tool.isStamp;
    }

    public boolean isCircleBrush(){
        return tool.isCircle;
    }
}
