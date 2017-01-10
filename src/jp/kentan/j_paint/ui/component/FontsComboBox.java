package jp.kentan.j_paint.ui.component;

import java.awt.*;

import javax.swing.*;

/*
http://www.java2s.com/Code/Java/2D-Graphics-GUI/Listallavailablefontsinthesystem.htm
 */
public class FontsComboBox extends JComboBox<String> {
    private Font[] fonts = null;

    public FontsComboBox() {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.fonts = gEnv.getAllFonts();
        for (Font font : fonts) {
            this.addItem(font.getName());
        }

        this.setSelectedIndex(0);
    }

    public Font getSelectedFont(){
        int index = this.getSelectedIndex();

        return fonts[index];
    }

    public static Font getDefaultFont(){
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return gEnv.getAllFonts()[0];
    }
}