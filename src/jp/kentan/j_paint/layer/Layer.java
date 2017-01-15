package jp.kentan.j_paint.layer;

import jp.kentan.j_paint.tool.ToolController;

import java.awt.*;
import java.awt.image.BufferedImage;


class Layer extends BufferedImage {
    private static final Color ALPHA = new Color(0, 0, 0, 0);

    private final LayerController controller;
    private final ToolController tool;

    private Graphics2D g;
    private Point p1, p2;
    private boolean isHide = false;
    private boolean isDragged = false;

    Layer(LayerController controller, ToolController tool, Dimension size){
        super(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR);

        this.controller = controller;
        this.tool = tool;

        g = this.createGraphics();
        g.setColor(Color.BLACK);
        g.setBackground(ALPHA);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        System.out.println("Layer create.(" + size.width + " * " + size.height + ")");
    }

    Layer(LayerController controller, ToolController tool, BufferedImage image){
        super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        this.controller = controller;
        this.tool = tool;

        g = this.createGraphics();
        g.setColor(Color.BLACK);
        g.setBackground(ALPHA);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        System.out.println("Layer create.(" + image + ")");
    }

    @Override
    protected void finalize() throws Throwable{
        try {
            super.finalize();
        } finally {
            this.flush();
            g.dispose();
            System.out.println("GC Layer destroyed.");
        }
    }

    void setHide(boolean isHide){
        this.isHide = isHide;
    }

    boolean isHide(){
        return  isHide;
    }

    void pressed(Point p){
        this.p1 = p;
    }

    void dragged(Point p){
        this.p2 = p;
        this.isDragged = true;

        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);

        g.setPaint(tool.getColor());
        g.setStroke(tool.getStroke());

        switch (tool.getType()){
            case LINE:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
            case RECT:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());

                if(tool.isFill()){
                    g.fillRoundRect(x, y, width, height, tool.getRoundRac(), tool.getRoundRac());
                }else{
                    if(tool.isShape()) g.drawRect(x, y, width, height);
                    else g.drawRoundRect(x, y, width, height, tool.getRoundRac(), tool.getRoundRac());
                }
                break;
            case OVAL:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(tool.getDashColor());
                g.setStroke(tool.getDashStroke());
                g.drawRect(x, y, width, height);

                g.setColor(tool.getColor());
                g.setStroke(tool.getStroke());

                if(tool.isFill()) g.fillOval(x, y, width, height);
                else              g.drawOval(x, y, width, height);
                break;
            case TEXT:
                if(tool.isStamp()) return;

                g.setFont(tool.getFont());
                g.setColor(tool.getColor());
                g.drawString(tool.getText(), p.x, p.y);
                break;
            case PEN:
            case BRUSH:
            case ERASER:
                g.setStroke(tool.getStroke());

                if(tool.isCircleBrush()){
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }else{
                    int plotX, plotY;
                    double dif = Math.hypot(p2.x-p1.x, p2.x-p1.y);
                    double difX = (p2.x-p1.x) / dif;
                    double difY = (p2.y-p1.y) / dif;
                    for (int i = 0; i < (int) dif; i++) {
                        plotX = p1.x + (int)(difX * i);
                        plotY = p1.y + (int)(difY * i);
                        g.drawLine(plotX, plotY, plotX, plotY);
                    }
                    g.drawLine(p1.x, p1.y, p1.x, p1.y);
                }

                p1 = p2;
                break;
            case DROPPER:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);
                g.setStroke(tool.getDashStroke());
                g.drawRect(p.x, p.y, 100, 50);

                Color color = tool.getPixelColor(p);
                g.setColor(color);
                g.setStroke(tool.getStroke());
                g.fillRect(p.x+1, p.y+1, 99, 49);

                controller.setDrawColor(color);
                break;
        }
    }

    boolean released(Point p){
        this.p2 = p;

        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);

        g.setColor(tool.getColor());
        g.setStroke(tool.getStroke());

        switch (tool.getType()){
            case LINE:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
            case RECT:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());

                if(tool.isFill()){
                    g.fillRoundRect(x, y, width, height, tool.getRoundRac(), tool.getRoundRac());
                }else{
                    if(tool.isShape()) g.drawRect(x, y, width, height);
                    else               g.drawRoundRect(x, y, width, height, tool.getRoundRac(), tool.getRoundRac());
                }
                break;
            case OVAL:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(tool.getColor());
                g.setStroke(tool.getStroke());

                if(tool.isFill()) g.fillOval(x, y, width, height);
                else              g.drawOval(x, y, width, height);
                break;
            case PEN:
            case BRUSH:
            case ERASER:
                g.setStroke(tool.getStroke());
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
            case DROPPER:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);
                g.setStroke(tool.getDashStroke());
                g.drawRect(p.x, p.y, 100, 50);

                Color color = tool.getPixelColor(p);
                g.setColor(color);
                g.setStroke(tool.getStroke());
                g.fillRect(p.x+1, p.y+1, 99, 49);

                controller.setDrawColor(color);
                return false;
        }

        return true;
    }

    void moved(Point p){
        g.setColor(tool.getColor());
        g.setStroke(tool.getStroke());

        switch (tool.getType()){
            case BRUSH:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.drawLine(p.x, p.y, p.x, p.y);
                break;
            case TEXT:
                if(p == null) p = p1;

                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setFont(tool.getFont());
                g.drawString(tool.getText(), p.x, p.y);

                p1 = p;
                break;
            case DROPPER:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);
                g.setStroke(tool.getDashStroke());
                g.drawRect(p.x, p.y, 100, 50);

                g.setColor(tool.getPixelColor(p));
                g.setStroke(tool.getStroke());
                g.fillRect(p.x+1, p.y+1, 99, 49);
                break;
        }
    }

    void exited(){
        switch (tool.getType()){
            case BRUSH:
            case TEXT:
                if(isDragged) return;
            case DROPPER:
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                break;
        }
    }

}
