package gui;

import data.Constants;
import graphics.Handler;
import graphics.Tile;
import input.Mouse;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.NeuralNetworkTrainer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.UnaryOperator;

public class Window
{
    public JFrame frame;
    private DrawingCanvas drawingCanvas;
    private ButtonPanel buttonPanel;
    private ResultPanel resultPanel;
    private Mouse mouse;
    private Handler handler;

    private NeuralNetwork neuralNetwork;

    public Window(int width, int height, String title)
    {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setBorder(new EmptyBorder(Constants.tileSize, Constants.tileSize, Constants.tileSize, Constants.tileSize));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initHandler();
        initNeuralNetwork();
        init();
        initGUI();

        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
    }

    public void show()
    {
        frame.setVisible(true);
    }

    private void initHandler()
    {
        handler = new Handler();
        for(int x = 0; x < Constants.tileNum; x++)
        {
            for(int y = 0; y < Constants.tileNum; y++)
            {
                handler.addTile(new Tile(x, y, Constants.tileSize, handler));
            }
        }
    }

    private void initNeuralNetwork()
    {
        UnaryOperator<Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
        UnaryOperator<Double> derivative = y -> y * (1 - y);

        neuralNetwork = new NeuralNetwork(sigmoid, derivative, 0.001, 784, 512, 128, 32, 10);

        NeuralNetworkTrainer neuralNetworkTrainer = new NeuralNetworkTrainer(neuralNetwork);
        neuralNetworkTrainer.read("resources\\output.dat");

        neuralNetworkTrainer.learn("resources\\test", 10000, 1000);
        neuralNetworkTrainer.saveResult("resources\\output.dat");

        /*long start = System.currentTimeMillis();
        neuralNetworkTrainer.learn("resources\\train", 60000, 4000);
        long end = System.currentTimeMillis();
        System.out.println("Training 1: " + (end - start) / 60 / 1000 + " min");

        neuralNetworkTrainer.saveResult("resources\\output.dat");

        neuralNetworkTrainer.read("resources\\output.dat");
        start = System.currentTimeMillis();
        neuralNetworkTrainer.learn("resources\\test", 10000, 1000);
        end = System.currentTimeMillis();
        System.out.println("Training 2: " + (end - start) / 60 / 1000 + " min");
        neuralNetworkTrainer.saveResult("resources\\output.dat");**/
    }

    private void init()
    {
        drawingCanvas = new DrawingCanvas(handler, Constants.width / 2, Constants.height / 2);
        buttonPanel = new ButtonPanel(handler, Constants.width, Constants.height / 20);
        resultPanel = new ResultPanel(handler, neuralNetwork, Constants.width / 4, Constants.height);
        mouse = new Mouse(handler);
    }

    private void initGUI()
    {
        frame.add(drawingCanvas, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(resultPanel, BorderLayout.EAST);
    }
}