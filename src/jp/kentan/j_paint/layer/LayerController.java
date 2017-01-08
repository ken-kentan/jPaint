package jp.kentan.j_paint.layer;

import jp.kentan.j_paint.JPaintController;
import jp.kentan.j_paint.tool.ToolController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;


public class LayerController extends JPanel implements MouseListener, MouseMotionListener {
    private JPaintController controller;
    private ToolController tool;

    private Layer layer;
    private List<Layer> layerList = new ArrayList<>();

    private int currentLayerIndex = 0, topLayerIndex = 0;

    private Dimension sizeCanvas;

    public LayerController(JPaintController controller, ToolController tool, Dimension sizeCanvas){
        this.controller = controller;
        this.tool = tool;

        this.setPreferredSize(sizeCanvas);
        this.setMinimumSize(sizeCanvas);
        this.setBackground(Color.WHITE); //背景色

        layer = new Layer(this.tool, sizeCanvas);

        this.sizeCanvas = sizeCanvas;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        System.out.println("LayerController create.");
    }

    public void undo(){
        if(currentLayerIndex <= 0) return;

        layerList.get(--currentLayerIndex).setHide(true);

        repaint();

        System.out.println("Layer(" + currentLayerIndex + ") hide.");
    }

    public void redo(){
        if(currentLayerIndex >= topLayerIndex) return;

        layerList.get(currentLayerIndex++).setHide(false);

        repaint();

        System.out.println("Layer(" + currentLayerIndex + ") show.");
    }

    public boolean canUndo(){
        return currentLayerIndex > 0;
    }

    public boolean canRedo(){
        return currentLayerIndex < topLayerIndex;
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
        layer = new Layer(this.tool, sizeCanvas);

        controller.updateCommandButtonStatus();
    }

    public void updateInputText(){
        layer.moved(null);
        repaint();
    }

    private boolean isLeftButton(MouseEvent e){
        return (e.getButton() == MouseEvent.BUTTON1 || (e.getModifiersEx() & (MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK)) == MouseEvent.BUTTON1_DOWN_MASK);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Layer layer : layerList){
            if(layer.getHide()) continue;

            g.drawImage(layer, 0, 0, layer.getWidth(), layer.getHeight(), this);
        }

        g.drawImage(layer, 0, 0, layer.getWidth(), layer.getHeight(), this);
    }

    public void mousePressed(MouseEvent e) {
        if(!isLeftButton(e)) return;

        layer.pressed(e.getPoint());

        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if(!isLeftButton(e)) return;

        layer.released(e.getPoint());

        createNewLayer();

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
        if (!isLeftButton(e)) return;

        layer.dragged(e.getPoint());

        repaint();
    }

    public void mouseMoved(MouseEvent e){
        layer.moved(e.getPoint());

        repaint();
    }
}