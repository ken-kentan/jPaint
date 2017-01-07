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
}
