package gui;

import graphics.Handler;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel
{
    private final Handler handler;
    private int width;
    private int height;

    private JButton clear;

    public ButtonPanel(Handler handler, int width, int height)
    {
        this.handler = handler;
        this.width = width;
        this.height = height;

        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        init();
        initGUI();
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

    private void init()
    {
        clear = new JButton("Clear canvas");
        clear.addActionListener(e -> handler.clear());
    }

    private void initGUI()
    {
        this.add(clear);
    }
}