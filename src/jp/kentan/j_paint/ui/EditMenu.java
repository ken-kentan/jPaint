package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.ui.component.CommandMenuItem;
import jp.kentan.j_paint.ui.component.ToolMenuItem;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class EditMenu extends JMenu {

    private ToolMenuItem itemCircleBrush, itemSquareBrush;
    private ToolMenuItem itemCircleEraser, itemSquareEraser, itemClearAll;
    private ToolMenuItem itemUndo, itemRedo;

    EditMenu(UIController controller, UIEventListener listener){
        super("編集");

        //親メニュー
        JMenu menuTool = new JMenu("ツール");
        JMenu menuBrush = new JMenu("Brush");
        JMenu menuEraser = new JMenu("Eraser");

        ToolMenuItem itemLineTool, itemRectTool, itemOvalTool, itemTextTool, itemPenTool, itemBrushTool, itemEraserTool;

        //Toolサブメニュー
        menuTool.add(itemLineTool   = new ToolMenuItem("直線", Tool.TYPE.LINE));
        menuTool.add(itemRectTool   = new ToolMenuItem("長方形 ", Tool.TYPE.RECT));
        menuTool.add(itemOvalTool   = new ToolMenuItem("楕円形", Tool.TYPE.OVAL));
        menuTool.add(itemTextTool   = new ToolMenuItem("テキスト", Tool.TYPE.TEXT));
        menuTool.add(itemPenTool    = new ToolMenuItem("ペン", Tool.TYPE.PEN));
        menuTool.add(itemBrushTool  = new ToolMenuItem("ブラシ", Tool.TYPE.BRUSH));
        menuTool.add(itemEraserTool = new ToolMenuItem("消しゴム", Tool.TYPE.ERASER));

        //Undo, Redoメニュー
        CommandMenuItem itemUndo = new CommandMenuItem("戻る", CommandMenuItem.CMD.UNDO);
        CommandMenuItem itemRedo = new CommandMenuItem("進む", CommandMenuItem.CMD.REDO);

        itemUndo.setMnemonic(KeyEvent.VK_Z);
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        itemRedo.setMnemonic(KeyEvent.VK_SHIFT + KeyEvent.VK_Z);
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

        //全消去
        JMenuItem itemClearAll = new JMenuItem("すべて消去");
        itemClearAll.setActionCommand("ClearAll");

        this.add(menuTool);
        this.addSeparator();
        this.add(itemUndo);
        this.add(itemRedo);
        this.addSeparator();
        this.add(itemClearAll);

        controller.add(itemLineTool);
        controller.add(itemRectTool);
        controller.add(itemOvalTool);
        controller.add(itemTextTool);
        controller.add(itemPenTool);
        controller.add(itemBrushTool);
        controller.add(itemEraserTool);
        controller.add(itemUndo);
        controller.add(itemRedo);

        itemLineTool.addActionListener(listener);
        itemRectTool.addActionListener(listener);
        itemOvalTool.addActionListener(listener);
        itemTextTool.addActionListener(listener);
        itemPenTool.addActionListener(listener);
        itemBrushTool.addActionListener(listener);
        itemEraserTool.addActionListener(listener);
        itemUndo.addActionListener(listener);
        itemRedo.addActionListener(listener);
        itemClearAll.addActionListener(listener);
    }
}
