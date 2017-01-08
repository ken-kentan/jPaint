package jp.kentan.j_paint.ui;

import javax.swing.*;


class MenuBar extends JMenuBar {
    MenuBar(UIEventListener listener){
        this.add(new FileMenu(listener));
    }
}
