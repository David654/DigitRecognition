package gui;

import data.Constants;
import graphics.Canvas;
import graphics.Handler;
import graphics.Tile;
import neuralnetwork.NeuralNetwork;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultPanel extends Canvas
{
    private final Handler handler;
    private final NeuralNetwork neuralNetwork;

    public ResultPanel(Handler handler, NeuralNetwork neuralNetwork, int width, int height)
    {
        super(width, height);
        this.handler = handler;
        this.neuralNetwork = neuralNetwork;
    }

    private double[] setInputNeurons()
    {
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int i = 0; i < Constants.tileNum; i++)
        {
            for(int j = 0; j < Constants.tileNum; j++)
            {
                Tile tile = handler.getTile(i, j);
                if(!tile.getColor().equals(Constants.bgColor))
                {
                    tiles.add(tile);
                }
            }
        }

        double[] inputNeurons = getCenteredImage(tiles);

        //printInputImage(inputNeurons);

        return inputNeurons;
    }

    private double[] getCenteredImage(ArrayList<Tile> tiles)
    {
        double[] inputNeurons = new double[handler.getNumOfTiles()];
        int minX = tiles.isEmpty() ? 0 : Collections.min(tiles, Comparator.comparingInt(Tile::getX)).getX();
        int minY = tiles.isEmpty() ? 0 : Collections.min(tiles, Comparator.comparingInt(Tile::getY)).getY();
        int maxX = tiles.isEmpty() ? 0 : Collections.max(tiles, Comparator.comparingInt(Tile::getX)).getX();
        int maxY = tiles.isEmpty() ? 0 : Collections.max(tiles, Comparator.comparingInt(Tile::getY)).getY();

        int cX = (minX + maxX) / 2;
        int cY = (minY + maxY) / 2;
        int centerX = Constants.tileNum / 2;
        int centerY = Constants.tileNum / 2;

        for(int i = 0; i < Constants.tileNum; i++)
        {
            for(int j = 0; j < Constants.tileNum; j++)
            {
                if((i >= minX && i <= maxX) && (j >= minY && j <= maxY))
                {
                    int x = i - cX + centerX;
                    int y = j - cY + centerY;
                    inputNeurons[x + y * Constants.tileNum] = handler.getTile(i ,j).getColor().getRed() / 255d;
                }
            }
        }
        return inputNeurons;
    }

    private void printInputImage(double[] inputNeurons)
    {
        for(int i = 0; i < Constants.tileNum; i++)
        {
            for(int j = 0; j < Constants.tileNum; j++)
            {
                if(inputNeurons[j + i * Constants.tileNum] == 1)
                {
                    System.out.print("▓");
                }
                else
                {
                    System.out.print("░");
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double[] outputNeurons = neuralNetwork.feedForward(setInputNeurons());

        int n = Constants.tileSize * 4;

        for(int i = 0; i < outputNeurons.length; i++)
        {
            double value = outputNeurons[i];

            g2d.setColor(Constants.bgColor);
            g2d.setFont(Constants.font);
            g2d.drawString(i + ": ", this.getWidth() / 32, this.getWidth() / 16 + n);
            g2d.drawString(String.format("%.1f", value * 100) + "%", this.getWidth() / 3 + this.getWidth() / 6 + this.getWidth() / 12, this.getWidth() / 16 + n);

            g2d.setColor(new Color((int) ((1 - value) * 255), (int) (value * 255), 0));

            g2d.fillRect(this.getWidth() / 6, this.getWidth() / 64 + n, (int) (value * 100), this.getWidth() / 16);

            n += this.getWidth() / 8;
        }

        g2d.setColor(Constants.bgColor);
        g2d.setFont(Constants.font.deriveFont(Constants.font.getSize() + 2f));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        String text = "Number: " + indexOfMax(outputNeurons);
        g2d.drawString(text, this.getWidth() / 32f + this.getWidth() / 3f - metrics.stringWidth(text) / 2f, this.getHeight() / 1.1f);
    }

    private int indexOfMax(double[] array)
    {
        int index = -1;
        double max = 0;
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] > max)
            {
                max = array[i];
                index = i;
            }
        }

        return index;
    }
}