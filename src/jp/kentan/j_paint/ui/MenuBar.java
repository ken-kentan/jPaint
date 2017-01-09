package jp.kentan.j_paint.ui;

import javax.swing.*;


class MenuBar extends JMenuBar {
    MenuBar(UIController controller, UIEventListener listener){
        this.add(new FileMenu(listener));
        this.add(new EditMenu(controller, listener));
    }
}
