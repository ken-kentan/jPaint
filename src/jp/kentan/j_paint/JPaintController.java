package jp.kentan.j_paint;

import jp.kentan.j_paint.layer.LayerController;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.tool.ToolController;
import jp.kentan.j_paint.ui.Dialog;
import jp.kentan.j_paint.ui.FileMenu;
import jp.kentan.j_paint.ui.UIController;
import jp.kentan.j_paint.ui.component.BrushRadioButton;
import jp.kentan.j_paint.ui.component.CornerRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class JPaintController {
    private UIController ui;
    private ToolController tool;
    private LayerController layer;

    private File path = null;

    JPaintController(){
        this.ui = new UIController(this);
        this.tool = new ToolController();
        this.layer = new LayerController(this, this.tool, this.ui.getCanvasSize());

        this.ui.setLayer(this.layer);

        setLayerTool(Tool.TYPE.LINE);

        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());

        updateWindowTitle();

        this.ui.setVisible(true);

        System.out.println("Welcome to jPaint! <3");
    }

    public void close(){
        if(!layer.isSaved()){
            switch (Dialog.showConfirmMsg("終了する前に変更を保存しますか？", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.YES_OPTION:
                    if(path != null && saveCanvas(null)){
                        break;
                    }else{ //untitledの場合
                        File file = Dialog.showSaveFileChooser();

                        if(file != null && saveCanvas(file)) break;
                        else return;
                    }
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
        }

        System.out.println("Bye bye.");
        System.exit(0);
    }

    public void updateWindowTitle(){
        boolean isSaved = layer.isSaved();

        if(path == null){
            ui.setTitle("jPaint -untitled" + (isSaved ? "" : " *"));
        }else{
            ui.setTitle("jPaint -" + path.getAbsolutePath() + (isSaved ? "" : " *"));
        }
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

        path = null;

        layer = null;
        ui.setLayer(this.layer = new LayerController(this, this.tool, size));
        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());

        setLayerTool(Tool.TYPE.LINE);

        updateWindowTitle();
    }

    public void createNewCanvasFromImage(File file){
        BufferedImage image;

        try {
            image = ImageIO.read(file);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Dialog.showWarningMsg("画像を開けませんでした。\nエラー詳細: " + e.getMessage());
            return;
        }

        path = file;

        layer = null;
        ui.setLayer(this.layer = new LayerController(this, this.tool, image));
        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());

        setLayerTool(Tool.TYPE.LINE);

        updateWindowTitle();
    }

    public boolean saveCanvas(File file){
        boolean isWrite;
        BufferedImage image;

        if(file == null){
            if(path == null) return false;

            file = path; //上書き保存
        }

        String suffix = FileMenu.getSuffix(file.getName());

        try{
            image = layer.merge(suffix);
            isWrite = ImageIO.write(image, suffix, file);
        } catch (Exception e){
            System.out.println(e);
            Dialog.showWarningMsg("保存に失敗しました。\nエラー詳細: " + (file.canWrite() ? e.getLocalizedMessage() : "ファイルへのアクセスが拒否されました。"));
            return false;
        }

        if(!isWrite){
            Dialog.showWarningMsg("保存に失敗しました。\n拡張子が正しく入力されているか確認して下さい。");
            return false;
        }

        layer.updateLayerIndex();

        path = file;
        updateWindowTitle();
        ui.setInfoText(" 画像を保存しました。");
        return true;
    }

    public void clearCanvas(){
        int option = Dialog.showConfirmWarningMsg("この操作は戻すことが出来ません。実行しますか？", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION){
            layer.clear();

            updateWindowTitle();
            updateCommandButtonStatus();
        }
    }

    public void undo(){
        layer.undo();
        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());
    }

    public void redo(){
        layer.redo();
        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());
    }

    public void updateCommandButtonStatus(){
        ui.updateCommandStatus(layer.canUndo(), layer.canRedo());
    }

    public void setLayerTool(Tool.TYPE type){
        if(type == Tool.TYPE.DROPPER) tool.setMergedImage(layer.merge(null));

        tool.set(type);
        ui.updateOptionBar(tool.getType(), tool.getSize(), tool.getCornerType(), tool.getBrushType(), tool.isFill());
        ui.updateToolStatus(type);
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
        ui.setColorPanel(color);
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

    public void setLayerToolFill(boolean isFill){
        tool.setFill(isFill);
    }

    public void setLayerToolTextBrush(boolean isBrush){
        tool.setTextBrush(isBrush);
    }

    //Canvasが消える場合に保存確認
    public boolean isSavedCanvas(){
        if(!layer.isSaved()){
            switch (Dialog.showConfirmMsg("変更を保存しますか？", JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.YES_OPTION:
                    if(path != null && saveCanvas(null)){
                        break;
                    }else{ //untitled
                        File file = Dialog.showSaveFileChooser();

                        if(file != null && saveCanvas(file)) break;
                        else return true;
                    }
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return true;
            }
        }

        return false;
    }

    public void updateInfoPointLabel(Point p){
        ui.setInfoText(" x=" + p.x + ", y=" +  p.y);
    }
}
