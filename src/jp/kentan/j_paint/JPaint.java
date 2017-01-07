package jp.kentan.j_paint;


/**
 * Created by kentaro on 2017/01/07.
 */
public class JPaint {
    private JPaint(){
        new JPaintController();
    }

    public static void main(String[] args) {
        new JPaint();
    }
}
