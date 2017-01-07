package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.layer.LayerController;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.ui.component.CommandButton;
import jp.kentan.j_paint.ui.component.CornerRadioButton;
import jp.kentan.j_paint.ui.component.ToolButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kentaro on 2017/01/07.
 */
public class UIController {
    private JPaintController controller;
    private Window window;

    private Dimension sizeCanvas = new Dimension(500, 500);

    private List<ToolButton> toolButtonList = new ArrayList<>();
    private List<CommandButton> cmdButtonList = new ArrayList<>();


    public UIController(JPaintController controller){
        this.controller = controller;

        UIEventListener listener = new UIEventListener(controller, this);

        this.window = new Window(this, listener);

        new Dialog(this.window);
    }

    public void setVisible(boolean visible){
        window.setVisible(visible);
    }

    void add(ToolButton button){
        toolButtonList.add(button);
    }

    void add(CommandButton button){
        cmdButtonList.add(button);
    }

    //選択されたToolButtonを無効化
    public void updateToolButtonStatus(Tool.TYPE type){
        for(ToolButton button : toolButtonList){
            button.setEnabled(button.get() != type);
        }
    }

    //選択されたToolButtonを無効化
    public void updateCommandButtonStatus(boolean canUndo, boolean canRedo){
        for(CommandButton button : cmdButtonList){
            if(button.get() == CommandButton.CMD.UNDO){
                button.setEnabled(canUndo);
            }else{
                button.setEnabled(canRedo);
            }
        }
    }

    //オプションパネル更新
    public void updateOptionBar(Tool.TYPE type, CornerRadioButton.TYPE typeCorner, int size){
        window.optionBar.title.setText(OptionBar.toolName[type.ordinal()]);
        updateToolSize(size);

        if(type == Tool.TYPE.LINE || type == Tool.TYPE.RECT){
            window.optionBar.setCornerButton(typeCorner);
        }else{
            window.optionBar.setCornerButton(null);
        }

        if(type == Tool.TYPE.LINE || type == Tool.TYPE.RECT || type == Tool.TYPE.OVAL || type == Tool.TYPE.BRUSH){
            window.optionBar.setSizePanel(true);
        }else{
            window.optionBar.setSizePanel(false);
        }

        window.optionBar.setFontPanel(type == Tool.TYPE.TEXT);
    }

    //オプションパネルのツール太さ更新
    public void updateToolSize(int size){
        window.optionBar.sizeSlider.setValue(size);
        window.optionBar.sizeTextField.setText(size + " px");
    }

    //オプションパネルのツール太さ復元
    public void restoreToolSize(){
        int size = window.optionBar.sizeSlider.getValue();
        window.optionBar.sizeTextField.setText(size + " px");
    }

    //オプションパネルのフォントサイズ更新
    public void updateFontSize(int size){
        window.optionBar.setFontSize(size + " pt");
    }

    //オプションパネルのフォントサイズ復元
    public void restoreFontSize(){
        window.optionBar.restoreFontSize();
    }

    //オプションパネルの入力テキスト更新
    public void updateInputText(String text){
        window.optionBar.setInputText(text);
    }

    //オプションパネルの入力テキスト復元
    public void restoreInputText(){
        window.optionBar.restoreInputText();
    }


    /*
    Setter
     */
    public void setLayer(LayerController layer){
        this.window.canvas.setLayer(layer);
    }


    /*
    Getter
     */
    public Dimension getCanvasSize(){
        return this.sizeCanvas;
    }
}
