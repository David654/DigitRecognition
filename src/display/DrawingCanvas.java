package display;

import data.Constants;
import drawing.Canvas;
import drawing.Handler;

import javax.swing.border.LineBorder;
import java.awt.*;

public class DrawingCanvas extends Canvas
{
    private final Handler handler;

    public DrawingCanvas(Handler handler, int width, int height)
    {
        super(width, height);
        this.handler = handler;
        this.setBorder(new LineBorder(Constants.fgColor));
        this.setBackground(Constants.bgColor);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        handler.update();
        handler.render(g2d);
    }
}