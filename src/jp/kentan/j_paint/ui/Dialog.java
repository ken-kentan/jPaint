package jp.kentan.j_paint.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by kentaro on 2017/01/07.
 */
public class Dialog {
    private static Window window;

    Dialog(Window _window){
        window = _window;

        System.out.println("Dialog initialized.");
    }

    public static int showConfirmMsg(String msg, int option){
        return JOptionPane.showConfirmDialog(window,
                msg, "jPaint", option);
    }

    public static void showWarningMsg(String msg){
        JOptionPane.showMessageDialog(window,
                msg, "jPaint",
                JOptionPane.WARNING_MESSAGE);
    }

    public static int showConfirmWarningMsg(String msg, int option){
        return JOptionPane.showConfirmDialog(window,
                msg, "jPaint", option,
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

    public static File showImageFileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("開く");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("すべての画像形式(*.JPG;*.JPEG;*.PNG)", "jpg", "jpeg", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG(*.JPG;*.JPEG)", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG(*.PNG)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setCurrentDirectory(new File
                (System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));

        if(fileChooser.showDialog(window, "開く") == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }else{
            return null;
        }
    }

    public static File showSaveFileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("名前を付けて保存");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("すべての画像形式(*.JPG;*.JPEG;*.PNG)", "jpg", "jpeg", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG(*.JPG;*.JPEG)", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG(*.PNG)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setCurrentDirectory(new File
                (System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));

        File file;
        while (true) {
            if (fileChooser.showSaveDialog(window) != JFileChooser.APPROVE_OPTION) return null;

            file = fileChooser.getSelectedFile();

            String suffix = FileMenu.getSuffix(file.getName());

            if(suffix == null) file = new File(file.getAbsoluteFile() + ".png");

            if (file.exists()) {
                switch (showConfirmMsg(file.getName() + " は既に存在します。\n上書きしますか？", JOptionPane.YES_NO_CANCEL_OPTION)) {
                    case JOptionPane.YES_OPTION:
                        break;
                    case JOptionPane.NO_OPTION:
                        continue;
                    case JOptionPane.CANCEL_OPTION:
                        return null;
                }
                break;
            } else {
                break;
            }
        }

        return file;
    }
}
