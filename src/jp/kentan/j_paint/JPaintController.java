package jp.kentan.j_paint;

import jp.kentan.j_paint.layer.LayerController;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.tool.ToolController;
import jp.kentan.j_paint.ui.Dialog;
import jp.kentan.j_paint.ui.UIController;
import jp.kentan.j_paint.ui.component.BrushRadioButton;
import jp.kentan.j_paint.ui.component.CornerRadioButton;

import java.awt.*;


public class JPaintController {
    private UIController ui;
    private LayerController layer;
    private ToolController tool;

    JPaintController(){
        this.ui = new UIController(this);
        this.tool = new ToolController();
        this.layer = new LayerController(this, this.tool, this.ui.getCanvasSize());

        this.ui.setLayer(this.layer);

        setLayerTool(Tool.TYPE.LINE);

        ui.updateCommandButtonStatus(layer.canUndo(), layer.canRedo());

        this.ui.setVisible(true);

        System.out.println("Welcome to jPaint! <3");
    }

    public void createNewCanvas(String[] strSize){
        Dimension size;
        try{
            int w, h;
            w = Integer.parseInt(strSize[0]);
            h = Integer.parseInt(strSize[1]);

            w = Math.max(1, w);
            h = Math.max(1, h);

            size = new Dimension(w, h);
        }catch (Exception exc){
            System.out.println(exc.getMessage());
            Dialog.showWarningMsg("無効な入力です。 整数を入力して下さい。");
            return;
        }

        if(size.width > 1920 || size.height > 1080){
            Dialog.showWarningMsg("無効な入力です。 最大サイズは 1920 pixel * 1080 pixelです。");
            return;
        }

        this.ui.setLayer(this.layer = new LayerController(this, this.tool, size));
    }

    public void undo(){
        layer.undo();
        ui.updateCommandButtonStatus(layer.canUndo(), layer.canRedo());
    }

    public void redo(){
        layer.redo();
        ui.updateCommandButtonStatus(layer.canUndo(), layer.canRedo());
    }

    public void updateCommandButtonStatus(){
        ui.updateCommandButtonStatus(layer.canUndo(), layer.canRedo());
    }

    public void setLayerTool(Tool.TYPE type){
        tool.set(type);
        ui.updateOptionBar(tool.getType(), tool.getSize(), tool.getCornerType(), tool.getBrushType());
        ui.updateToolButtonStatus(type);
    }

    public void setLayerToolSize(String strSize){
        int size;

        try {
            size = Integer.parseInt(strSize.replace("px", "").replaceAll(" ", ""));
            size = Math.max(1, Math.min(100, size));
        }catch (Exception e){
            Dialog.showWarningMsg("無効な入力です。 1 pixel から 100 pixel の整数を入力して下さい。");
            ui.restoreToolSize();
            return;
        }

        tool.set(size);
        ui.updateToolSize(size);
    }

    public void setLayerFontSize(String strSize){
        int size;

        try {
            size = Integer.parseInt(strSize.replace("px", "").replaceAll(" ", ""));
            size = Math.max(1, Math.min(1000, size));
        }catch (Exception e){
            Dialog.showWarningMsg("無効な入力です。 1 pixel から 1000 pixel の整数を入力して下さい。");
            ui.restoreFontSize();
            return;
        }

        tool.set(size);
        ui.updateFontSize(size);
    }

    public void setLayerToolSize(int size){
        tool.set(size);
        ui.updateToolSize(size);
    }

    public void setLayerToolColor(Color color){
        tool.set(color);
    }

    public void setLayerToolCornerType(CornerRadioButton.TYPE type){
        tool.setShape(type == CornerRadioButton.TYPE.SHARP);
    }

    public void setLayerToolBrushType(BrushRadioButton.TYPE type){
        tool.setBrush(type == BrushRadioButton.TYPE.CIRCLE);
    }

    public void setLayerToolFont(Font font){
        tool.set(font);
    }

    public void setLayerToolText(String text){
        if(text == null || text.length() <= 0){
            tool.set("|");
            layer.updateInputText();
            return;
        }

        tool.set(text);
        layer.updateInputText();
    }

    public void setLayerToolTextBrush(boolean isBrush){
        tool.setTextBrush(isBrush);
    }
}
