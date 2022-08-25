package input;

import data.Constants;
import graphics.Handler;
import graphics.Tile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter
{
    private final Handler handler;
    private int mouseButton;

    public Mouse(Handler handler)
    {
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e)
    {
        mouseButton = e.getButton();
        if(mouseButton == MouseEvent.BUTTON1)
        {
            setColor(e, Constants.fgColor);
        }

        if(mouseButton == MouseEvent.BUTTON3)
        {
            setColor(e, Constants.bgColor);
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        if(mouseButton == MouseEvent.BUTTON1)
        {
            setColor(e, Constants.fgColor);
        }

        if(mouseButton == MouseEvent.BUTTON3)
        {
            setColor(e, Constants.bgColor);
        }
    }

    private void setColor(MouseEvent e, Color color)
    {
        Tile tile1 = handler.getTile(getX(e), getY(e));
        //Tile tile2 = handler.getTile(getX(e) + 1, getY(e));
        //Tile tile3 = handler.getTile(getX(e) + 1, getY(e) + 1);
        //Tile tile4 = handler.getTile(getX(e), getY(e) + 1);
        if(tile1 != null) tile1.setColor(color);
        //if(tile2 != null) tile2.setColor(color);
        //if(tile3 != null) tile3.setColor(color);
        //if(tile4 != null) tile4.setColor(color);
    }

    private int getX(MouseEvent e)
    {
        int x = e.getX() - Constants.tileSize - Constants.tileSize / 2;
        return x / Constants.tileSize;
    }

    private int getY(MouseEvent e)
    {
        int y = e.getY() - 2 * Constants.tileSize;
        return y / Constants.tileSize;
    }
}