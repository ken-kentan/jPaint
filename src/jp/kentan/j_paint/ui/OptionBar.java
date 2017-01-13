package jp.kentan.j_paint.ui;

import jp.kentan.j_paint.resources.R;
import jp.kentan.j_paint.ui.component.BrushRadioButton;
import jp.kentan.j_paint.ui.component.FontsComboBox;
import jp.kentan.j_paint.ui.component.CornerRadioButton;

import javax.swing.*;
import java.awt.*;


class OptionBar extends JPanel {
    private static final int[] DEFAULT_FONT_SIZE = {6, 8, 9, 10, 11, 12, 14, 18, 24, 30, 36, 48, 60, 72};

    private JComboBox<String> fontSizeBox;
    private CornerRadioButton radioSharp, radioRound;
    private BrushRadioButton radioCircle, radioSquare;
    private JPanel panelSize, panelStyle, panelText, panelStyleBrush;
    private String prevFontSize = "50 px";

    JLabel title;
    JTextField sizeTextField;
    JSlider sizeSlider;

    static final String[] toolName = new String[]{"直線", "長方形", "楕円形", "テキスト", "ペン", "ブラシ", "スポイド", "消しゴム"};

    OptionBar(UIEventListener listener){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setPreferredSize(new Dimension(1920, 30));
        this.setBackground(Color.LIGHT_GRAY);

        title = new JLabel(toolName[0]);

        /*
        太さパネル
         */
        panelSize = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panelSize.setPreferredSize(new Dimension(190, 20));
        panelSize.setBackground(Color.LIGHT_GRAY);

        sizeTextField = new JTextField("1 px");
        sizeTextField.setName("ToolSize");
        sizeTextField.setHorizontalAlignment(JTextField.RIGHT);
        sizeTextField.setBorder(null);
        sizeTextField.setPreferredSize(new Dimension(50, 20));

        sizeSlider = new JSlider();
        sizeSlider.setPreferredSize(new Dimension(90, 20));
        sizeSlider.setBackground(Color.LIGHT_GRAY);
        sizeSlider.setMinimum(1);
        sizeSlider.setValue(1);

        panelSize.add(getSeparator());

        panelSize.add(new JLabel("太さ: "));
        panelSize.add(sizeTextField);
        panelSize.add(sizeSlider);
        //end

        /*
        スタイルパネル
         */
        panelStyle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelStyle.setPreferredSize(new Dimension(140, 20));
        panelStyle.setBackground(Color.LIGHT_GRAY);

        ButtonGroup groupCorner = new ButtonGroup();
        radioSharp = new CornerRadioButton(CornerRadioButton.TYPE.SHARP);
        radioRound = new CornerRadioButton(CornerRadioButton.TYPE.ROUND);

        radioSharp.setPreferredSize(new Dimension(18, 20));
        radioSharp.setBackground(Color.LIGHT_GRAY);
        radioRound.setPreferredSize(new Dimension(18, 20));
        radioRound.setBackground(Color.LIGHT_GRAY);

        groupCorner.add(radioSharp);
        groupCorner.add(radioRound);

        panelStyle.add(getSeparator());
        panelStyle.add(new JLabel("スタイル:"));
        panelStyle.add(radioSharp);
        panelStyle.add(new JLabel(R.SHARP));
        panelStyle.add(radioRound);
        panelStyle.add(new JLabel(R.ROUND));
        // end

        /*
        テキストパネル
         */
        panelText = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panelText.setPreferredSize(new Dimension(900, 20));
        panelText.setBackground(Color.LIGHT_GRAY);

        FontsComboBox fontsComboBox = new FontsComboBox();
        fontsComboBox.setPreferredSize(new Dimension(250, 18));

        fontSizeBox = new JComboBox<>();
        fontSizeBox.setPreferredSize(new Dimension(70, 18));
        fontSizeBox.setEditable(true);

        for(int i : DEFAULT_FONT_SIZE) {
            fontSizeBox.addItem(i + " px");
        }

        fontSizeBox.setSelectedItem(prevFontSize);

        JTextField inputTextField = new JTextField("Text");
        inputTextField.setName("InputText");
        inputTextField.setBorder(null);
        inputTextField.setPreferredSize(new Dimension(200, 20));

        JCheckBox brushCheckBox = new JCheckBox("ブラシ");
        brushCheckBox.setName("TextBrushOption");
        brushCheckBox.setBackground(Color.LIGHT_GRAY);
        brushCheckBox.setPreferredSize(new Dimension(100, 20));

        panelText.add(getSeparator());

        panelText.add(new JLabel("フォント:"));
        panelText.add(fontsComboBox);
        panelText.add(new JLabel(" サイズ:"));
        panelText.add(fontSizeBox);
        panelText.add(new JLabel(" 入力:"));
        panelText.add(inputTextField);

        panelText.add(getSeparator());

        panelText.add(new JLabel(" オプション:"));
        panelText.add(brushCheckBox);

        panelText.setVisible(false);
        //end

        /*
        ブラシスタイルパネル
         */
        panelStyleBrush = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelStyleBrush.setPreferredSize(new Dimension(140, 20));
        panelStyleBrush.setBackground(Color.LIGHT_GRAY);

        ButtonGroup groupBrush = new ButtonGroup();
        radioCircle = new BrushRadioButton(BrushRadioButton.TYPE.CIRCLE);
        radioSquare = new BrushRadioButton(BrushRadioButton.TYPE.SQUARE);

        radioCircle.setPreferredSize(new Dimension(18, 20));
        radioCircle.setBackground(Color.LIGHT_GRAY);
        radioSquare.setPreferredSize(new Dimension(18, 20));
        radioSquare.setBackground(Color.LIGHT_GRAY);

        groupBrush.add(radioCircle);
        groupBrush.add(radioSquare);

        panelStyleBrush.add(getSeparator());
        panelStyleBrush.add(new JLabel("スタイル:"));
        panelStyleBrush.add(radioCircle);
        panelStyleBrush.add(new JLabel(R.CIRCLE));
        panelStyleBrush.add(radioSquare);
        panelStyleBrush.add(new JLabel(R.SQUARE));
        // end

        this.add(title);
        this.add(panelSize);
        this.add(panelStyle);
        this.add(panelText);
        this.add(panelStyleBrush);

        sizeTextField.addActionListener(listener);
        sizeSlider.addChangeListener(listener);
        radioSharp.addActionListener(listener);
        radioRound.addActionListener(listener);
        fontsComboBox.addItemListener(listener);
        fontSizeBox.addItemListener(listener);
        inputTextField.getDocument().addDocumentListener(listener);
        brushCheckBox.addActionListener(listener);
        radioCircle.addActionListener(listener);
        radioSquare.addActionListener(listener);
    }

    void setCornerButton(CornerRadioButton.TYPE type){
        if(type == null){
            panelStyle.setVisible(false);
        }else{
            panelStyle.setVisible(true);

            radioSharp.setSelected(type == CornerRadioButton.TYPE.SHARP);
            radioRound.setSelected(type == CornerRadioButton.TYPE.ROUND);
        }
    }

    void setBrushButton(BrushRadioButton.TYPE type){
        if(type == null){
            panelStyleBrush.setVisible(false);
        }else{
            panelStyleBrush.setVisible(true);

            radioCircle.setSelected(type == BrushRadioButton.TYPE.CIRCLE);
            radioSquare.setSelected(type == BrushRadioButton.TYPE.SQUARE);
        }
    }

    void setSizePanel(boolean isVisible){
        panelSize.setVisible(isVisible);
    }

    void setFontPanel(boolean isVisible){
        panelText.setVisible(isVisible);
    }

    void setFontSize(String fontSize){
        if(!fontSize.equals(prevFontSize)){
            prevFontSize = fontSize;
            fontSizeBox.setSelectedItem(fontSize);
        }
    }

    void restoreFontSize(){
        fontSizeBox.setSelectedItem(prevFontSize);
    }

    private JSeparator getSeparator(){
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(5, 20));
        return separator;
    }
}
