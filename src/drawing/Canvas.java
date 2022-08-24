package drawing;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel
{
    protected int width;
    protected int height;

    public Canvas(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.setPreferredSize(new Dimension(width, height));

        Timer timer = new Timer(1, e -> repaint());
        timer.start();
    }

    public void setWidth(int width)
    {
        this.width = width;
        this.setPreferredSize(new Dimension(width, height));
    }

    public void setHeight(int height)
    {
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
    }
}