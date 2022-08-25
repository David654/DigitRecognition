package gui;

import data.Constants;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch(Exception e) {System.err.println("Unable to load System look and feel");}

        MainWindow window = new MainWindow(Constants.width, Constants.height, Constants.title);
        window.show();
    }
}