package jp.kentan.j_paint.layer;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.tool.ToolController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class LayerController extends JPanel implements MouseListener, MouseMotionListener {
    private static final String[] SUFFIX_ALPHA_SUPPORT = new String[]{"png", "gif", "PNG", "GIF"};

    private JPaintController controller;
    private ToolController tool;

    private Layer layer, background = null;
    private List<Layer> layerList = new ArrayList<>();

    private int currentLayerIndex = 0, topLayerIndex = 0, saveLayerIndex = 0;

    private Dimension sizeCanvas;

    public LayerController(JPaintController controller, ToolController tool, Dimension sizeCanvas){
        this.controller = controller;
        this.tool = tool;

        this.setPreferredSize(sizeCanvas);
        this.setMinimumSize(sizeCanvas);
        this.setBackground(Color.WHITE); //背景色

        layer = new Layer(this, this.tool, sizeCanvas);

        this.sizeCanvas = sizeCanvas;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        System.out.println("LayerController create.");
    }

    public LayerController(JPaintController controller, ToolController tool, BufferedImage image){
        this.controller = controller;
        this.tool = tool;

        Dimension sizeCanvas = new Dimension(image.getWidth(), image.getHeight());

        this.setPreferredSize(sizeCanvas);
        this.setMinimumSize(sizeCanvas);
        this.setBackground(Color.WHITE); //背景色

        background = new Layer(this, this.tool, image);

        layer = new Layer(this, this.tool, sizeCanvas);

        this.sizeCanvas = sizeCanvas;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        System.out.println("LayerController create.");
    }

    @Override
    protected void finalize() throws Throwable{
        try {
            super.finalize();
        } finally {
            layerList.clear();
            System.out.println("GC LayerController destroyed.");
        }
    }

    public void clear(){
        int size = layerList.size();

        layerList.clear();

        currentLayerIndex = topLayerIndex = 0;

        repaint();

        System.out.println("All Layer(" + size + ") clear.");
    }

    public void undo(){
        if(currentLayerIndex <= 0) return;

        layerList.get(--currentLayerIndex).setHide(true);

        repaint();

        controller.updateWindowTitle();
        System.out.println("Layer(" + currentLayerIndex + ") hide.");
    }

    public void redo(){
        if(currentLayerIndex >= topLayerIndex) return;

        layerList.get(currentLayerIndex++).setHide(false);

        repaint();

        controller.updateWindowTitle();
        System.out.println("Layer(" + currentLayerIndex + ") show.");
    }

    public boolean canUndo(){
        return currentLayerIndex > 0;
    }

    public boolean canRedo(){
        return currentLayerIndex < topLayerIndex;
    }

    public boolean isSaved(){
        return saveLayerIndex == currentLayerIndex;
    }

    private void createNewLayer(){
        //最上部レイヤーでない場合
        if(currentLayerIndex < topLayerIndex){
            for(int i = topLayerIndex; i > currentLayerIndex; --i){
                layerList.remove(i - 1);

                System.out.println("Layer(" + (i - 1) + ") remove.");
            }

            topLayerIndex = ++ currentLayerIndex;
        }else{
            currentLayerIndex = ++topLayerIndex;
        }

        layerList.add(layer);
        layer = new Layer(this, tool, sizeCanvas);

        controller.updateWindowTitle();
        controller.updateCommandButtonStatus();
    }

    public BufferedImage merge(String suffix){
        BufferedImage image;

        if(suffix == null) suffix = "png";

        if(isSupportAlpha(suffix)){
            image = new BufferedImage(sizeCanvas.width, sizeCanvas.height, BufferedImage.TYPE_4BYTE_ABGR);
        }else{
            image = new BufferedImage(sizeCanvas.width, sizeCanvas.height, BufferedImage.TYPE_3BYTE_BGR);
        }

        Graphics g = image.getGraphics();

        //白背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sizeCanvas.width, sizeCanvas.height);

        if(background != null) g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), this);

        for(Layer layer : layerList){
            if(layer.isHide()) continue;
            g.drawImage(layer, 0, 0, layer.getWidth(), layer.getHeight(), this);
        }

        System.out.println("image merged.(" + layerList.size() + " layers)");

        return image;
    }

    //現在のレイヤーを保存レイヤーに指定
    public void updateLayerIndex(){
        saveLayerIndex = currentLayerIndex;
    }

    //静止状態でテキストが更新された際に強制的に再描写
    public void updateInputText(){
        layer.moved(null);
        repaint();
    }

    void setDrawColor(Color color){
        controller.setLayerToolColor(color);
    }

    private boolean isRightButton(MouseEvent e){
        return !(e.getButton() == MouseEvent.BUTTON1 || (e.getModifiersEx() & (MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK)) == MouseEvent.BUTTON1_DOWN_MASK);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(background != null) g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), this);

        for(Layer layer : layerList){
            if(layer.isHide()) continue;

            g.drawImage(layer, 0, 0, layer.getWidth(), layer.getHeight(), this);
        }

        g.drawImage(layer, 0, 0, layer.getWidth(), layer.getHeight(), this);
    }

    public void mousePressed(MouseEvent e) {
        if(isRightButton(e)) return;

        layer.pressed(e.getPoint());

        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if(isRightButton(e)) return;

        if(layer.released(e.getPoint())){
            createNewLayer();
        }

        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        layer.exited();
        repaint();
    }

    public void mouseDragged(MouseEvent e){
        if (isRightButton(e)) return;

        layer.dragged(e.getPoint());
        controller.updateInfoPointLabel(e.getPoint());

        repaint();
    }

    public void mouseMoved(MouseEvent e){
        layer.moved(e.getPoint());
        controller.updateInfoPointLabel(e.getPoint());

        repaint();
    }

    private boolean isSupportAlpha(String suffix){
        for(String suffixAlpha : SUFFIX_ALPHA_SUPPORT){
            if(suffix.equals(suffixAlpha)) return true;
        }

        return false;
    }

    public Dimension getSize(){
        return sizeCanvas;
    }
}
