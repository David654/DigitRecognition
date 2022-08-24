package drawing;

import data.Constants;

import java.awt.*;

public class Tile
{
    private final Handler handler;
    private final int x;
    private final int y;
    private final int size;
    private Color color;
    private Color originalColor;

    public Tile(int x, int y, int size, Handler handler)
    {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.size = size;
        color = Constants.bgColor;
        originalColor = color;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getSize()
    {
        return size;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.originalColor = color;
    }

    public void update()
    {
        color = originalColor;
        color = getSubColor(originalColor);
    }

    private boolean checkCell(int x, int y)
    {
        return x >= 0 && x <= Constants.tileNum - 1 && y >= 0 && y <= Constants.tileNum - 1;
    }

    private Color getSubColor(Color color)
    {
        boolean top = checkCell(x, y - 1);
        boolean topRight = checkCell(x + 1, y - 1);
        boolean right = checkCell(x + 1, y);
        boolean bottomRight = checkCell(x + 1, y + 1);
        boolean bottom = checkCell(x, y + 1);
        boolean bottomLeft = checkCell(x - 1, y + 1);
        boolean left = checkCell(x - 1, y);
        boolean topLeft = checkCell(x - 1, y - 1);

        int topIndex = x * Constants.tileNum + y - 1;
        int topRightIndex = (x + 1) * Constants.tileNum + y - 1;
        int rightIndex = (x + 1) * Constants.tileNum + y;
        int bottomRightIndex = (x + 1) * Constants.tileNum + y + 1;
        int bottomIndex = x * Constants.tileNum + y + 1;
        int bottomLeftIndex = (x - 1) * Constants.tileNum + y + 1;
        int leftIndex = (x - 1) * Constants.tileNum + y;
        int topLeftIndex = (x - 1) * Constants.tileNum + y - 1;

        int[] sum = new int[4];
        int r = 0, g = 0, b = 0;

        if(color != Constants.fgColor)
        {
            if(top) sum = getColor(sum, topIndex);
            if(topRight) sum = getColor(sum, topRightIndex);
            if(right) sum = getColor(sum, rightIndex);
            if(bottomRight) sum = getColor(sum, bottomRightIndex);
            if(bottom) sum = getColor(sum, bottomIndex);
            if(bottomLeft) sum = getColor(sum, bottomLeftIndex);
            if(left) sum = getColor(sum, leftIndex);
            if(topLeft) sum = getColor(sum, topLeftIndex);

            r = (int) (sum[0] / sum[3] * 0.6);
            g = (int) (sum[1] / sum[3] * 0.6);
            b = (int) (sum[2] / sum[3] * 0.6);
        }

        return new Color(Math.min(255, r + color.getRed()), Math.min(255, g + color.getGreen()), Math.min(255, b + color.getBlue()));
    }

    private int[] getColor(int[] sum, int index)
    {
        sum[0] += handler.getTile(index).getColor().getRed();
        sum[1] += handler.getTile(index).getColor().getGreen();
        sum[2] += handler.getTile(index).getColor().getBlue();
        sum[3]++;
        return new int[] {sum[0], sum[1], sum[2], sum[3]};
    }

    public String toString()
    {
        return "X: " + x + "; Y: " + y + "; Color: " + color.toString();
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(color);
        g2d.fillRect(x * size, y * size, size, size);
    }
}