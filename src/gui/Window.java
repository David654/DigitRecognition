package gui;

import data.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public abstract class Window
{
    protected final JFrame frame;

    public Window(int width, int height, String title)
    {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setBorder(new EmptyBorder(Constants.tileSize, Constants.tileSize, Constants.tileSize, Constants.tileSize));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGUI();
    }

    protected abstract void initGUI();
}