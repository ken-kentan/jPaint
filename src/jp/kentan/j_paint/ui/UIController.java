package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.layer.LayerController;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.ui.component.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class UIController {
    private Window window;

    private Dimension sizeCanvas = new Dimension(500, 500);

    private List<ToolButton> toolButtonList = new ArrayList<>();
    private List<CommandButton> cmdButtonList = new ArrayList<>();
    private List<ToolMenuItem> toolMenuItemList = new ArrayList<>();
    private List<CommandMenuItem> cmdMenuItemList = new ArrayList<>();


    public UIController(JPaintController controller){
        UIEventListener listener = new UIEventListener(controller);

        this.window = new Window(this, listener);

        new Dialog(this.window);
    }

    public void setVisible(boolean isVisible){
        window.setVisible(isVisible);
    }

    public void setTitle(String title){
        window.setTitle(title);
    }

    void add(ToolButton button){
        toolButtonList.add(button);
    }

    void add(CommandButton button){
        cmdButtonList.add(button);
    }

    void add(ToolMenuItem item){
        toolMenuItemList.add(item);
    }

    void add(CommandMenuItem item){
        cmdMenuItemList.add(item);
    }

    //選択されたToolを無効化
    public void updateToolStatus(Tool.TYPE type){
        for(ToolButton button : toolButtonList){
            button.setEnabled(button.get() != type);
        }

        for(ToolMenuItem item : toolMenuItemList){
            item.setEnabled(item.get() != type);
        }
    }

    //選択されたCommandを無効化
    public void updateCommandStatus(boolean canUndo, boolean canRedo){
        for(CommandButton button : cmdButtonList){
            if(button.get() == CommandButton.CMD.UNDO){
                button.setEnabled(canUndo);
            }else{
                button.setEnabled(canRedo);
            }
        }

        for(CommandMenuItem item : cmdMenuItemList){
            if(item.get() == CommandMenuItem.CMD.UNDO){
                item.setEnabled(canUndo);
            }else{
                item.setEnabled(canRedo);
            }
        }
    }

    //オプションパネル更新
    public void updateOptionBar(Tool.TYPE type, int size, CornerRadioButton.TYPE typeCorner, BrushRadioButton.TYPE typeBrush){
        window.option.title.setText(OptionBar.toolName[type.ordinal()]);
        updateToolSize(size);

        if(type == Tool.TYPE.LINE || type == Tool.TYPE.RECT){
            window.option.setCornerButton(typeCorner);
        }else{
            window.option.setCornerButton(null);
        }

        if(type == Tool.TYPE.LINE || type == Tool.TYPE.RECT || type == Tool.TYPE.OVAL || type == Tool.TYPE.BRUSH || type == Tool.TYPE.ERASER){
            window.option.setSizePanel(true);
        }else{
            window.option.setSizePanel(false);
        }

        if(type == Tool.TYPE.BRUSH || type == Tool.TYPE.ERASER){
            window.option.setBrushButton(typeBrush);
        }else{
            window.option.setBrushButton(null);
        }

        window.option.setFontPanel(type == Tool.TYPE.TEXT);
    }

    //オプションパネルのツール太さ更新
    public void updateToolSize(int size){
        window.option.sizeSlider.setValue(size);
        window.option.sizeTextField.setText(size + " px");
    }

    //オプションパネルのツール太さ復元
    public void restoreToolSize(){
        int size = window.option.sizeSlider.getValue();
        window.option.sizeTextField.setText(size + " px");
    }

    //オプションパネルのフォントサイズ更新
    public void updateFontSize(int size){
        window.option.setFontSize(size + " px");
    }

    //オプションパネルのフォントサイズ復元
    public void restoreFontSize(){
        window.option.restoreFontSize();
    }


    /*
    Setter
     */
    public void setLayer(LayerController layer){
        window.canvas.setLayer(layer);
        window.info.updateCanvasSize(layer.getSize());
    }

    public void setInfoText(String text){
        window.info.setText(text);
    }

    public void setColorPanel(Color color){
        window.sideBar.setColorPanel(color);
    }


    /*
    Getter
     */
    public Dimension getCanvasSize(){
        return this.sizeCanvas;
    }
}
