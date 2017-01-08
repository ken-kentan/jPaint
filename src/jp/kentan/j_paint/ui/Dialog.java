package jp.kentan.j_paint.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kentaro on 2017/01/07.
 */
public class Dialog {
    private static Window window;

    Dialog(Window _window){
        window = _window;

        System.out.println("Dialog initialized.");
    }

    public static void showWarningMsg(String msg){
        JOptionPane.showMessageDialog(window,
                msg, "jPaint",
                JOptionPane.WARNING_MESSAGE);
    }

    public static Color showColorChooser(Color color){
        return JColorChooser.showDialog(window, "カラーピッカー(描画色)", color);
    }

    public static String[] showNewCanvasDialog() {
        String titleBtn[] = {"作成", "キャンセル"};
        JPanel panel = new JPanel(new BorderLayout());

        JPanel labels = new JPanel(new GridLayout(0,1));
        labels.add(new JLabel("幅(W) : ", SwingConstants.RIGHT));
        labels.add(new JLabel("高さ(H) : ", SwingConstants.RIGHT));
        panel.add(labels, BorderLayout.WEST);

        JPanel textField = new JPanel(new GridLayout(0,1));
        JTextField width = new JTextField("500");
        textField.add(width);
        JTextField height = new JTextField("500");
        textField.add(height);
        panel.add(textField, BorderLayout.CENTER);

        JPanel px = new JPanel(new GridLayout(0,1));
        px.add(new JLabel(" pixel"));
        px.add(new JLabel(" pixel"));
        panel.add(px, BorderLayout.EAST);

        if(JOptionPane.showOptionDialog(window, panel, "新規",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                titleBtn,
                titleBtn[0]) == JOptionPane.YES_OPTION){

            return new String[]{width.getText(), height.getText()};
        }else{
            return null;
        }
    }
}
