package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.resources.R;
import jp.kentan.j_paint.tool.Tool;
import jp.kentan.j_paint.ui.component.CommandButton;
import jp.kentan.j_paint.ui.component.ToolButton;

import javax.swing.*;
import java.awt.*;


class SideBar extends JPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(40, 40);

    SideBar(UIController controller, UIEventListener listener){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(100, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel panelTools = new JPanel();
        panelTools.setLayout(new GridBagLayout());

        ToolButton btnLineTool = new ToolButton(R.LINE, Tool.TYPE.LINE);
        ToolButton btnRectTool = new ToolButton(R.RECT, Tool.TYPE.RECT);
        ToolButton btnOvalTool = new ToolButton(R.OVAL, Tool.TYPE.OVAL);
        ToolButton btnTextTool = new ToolButton(R.TEXT, Tool.TYPE.TEXT);
        ToolButton btnPenTool  = new ToolButton(R.PEN, Tool.TYPE.PEN);
        ToolButton btnBrushTool = new ToolButton(R.BRUSH, Tool.TYPE.BRUSH);
        ToolButton btnEraserTool = new ToolButton(R.ERASER, Tool.TYPE.ERASER);

        CommandButton btnUndo  = new CommandButton(R.UNDO, CommandButton.CMD.UNDO);
        CommandButton btnRedo = new CommandButton(R.REDO, CommandButton.CMD.REDO);

        btnLineTool.setPreferredSize(BUTTON_SIZE);
        btnRectTool.setPreferredSize(BUTTON_SIZE);
        btnOvalTool.setPreferredSize(BUTTON_SIZE);
        btnTextTool.setPreferredSize(BUTTON_SIZE);
        btnPenTool.setPreferredSize(BUTTON_SIZE);
        btnBrushTool.setPreferredSize(BUTTON_SIZE);
        btnEraserTool.setPreferredSize(BUTTON_SIZE);

        btnUndo.setPreferredSize(BUTTON_SIZE);
        btnRedo.setPreferredSize(BUTTON_SIZE);

        JPanel btnColor = new JPanel();
        btnColor.setName("Color");
        btnColor.setBackground(Color.BLACK);
        btnColor.setPreferredSize(new Dimension(0, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelTools.add(btnLineTool, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelTools.add(btnRectTool, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelTools.add(btnOvalTool, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelTools.add(btnTextTool, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelTools.add(btnPenTool, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelTools.add(btnBrushTool, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelTools.add(btnEraserTool, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 0);
        panelTools.add(btnUndo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelTools.add(btnRedo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 0, 0);
        panelTools.add(btnColor, gbc);

        controller.add(btnLineTool);
        controller.add(btnRectTool);
        controller.add(btnOvalTool);
        controller.add(btnTextTool);
        controller.add(btnPenTool);
        controller.add(btnBrushTool);
        controller.add(btnEraserTool);

        controller.add(btnUndo);
        controller.add(btnRedo);

        btnLineTool.addActionListener(listener);
        btnRectTool.addActionListener(listener);
        btnOvalTool.addActionListener(listener);
        btnTextTool.addActionListener(listener);
        btnPenTool.addActionListener(listener);
        btnBrushTool.addActionListener(listener);
        btnEraserTool.addActionListener(listener);

        btnUndo.addActionListener(listener);
        btnRedo.addActionListener(listener);

        btnColor.addMouseListener(listener);

        this.add(panelTools);
    }
}
