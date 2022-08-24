package drawing;

import data.Constants;

import java.awt.*;
import java.util.ArrayList;

public class Handler
{
    private final ArrayList<Tile> tiles;

    public Handler()
    {
        tiles = new ArrayList<>();
    }

    public int getNumOfTiles()
    {
        return tiles.size();
    }

    public void addTile(Tile tile)
    {
        tiles.add(tile);
    }

    public Tile getTile(int index)
    {
        return tiles.get(index);
    }

    public Tile getTile(int x, int y)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);
            if(tile.getX() == x && tile.getY() == y)
            {
                return tile;
            }
        }
        return null;
    }

    public void clear()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);
            tile.setColor(Constants.bgColor);
        }
    }

    public void update()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);
            tile.update();
        }
    }

    public void render(Graphics2D g2d)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);
            tile.render(g2d);
        }
    }
}