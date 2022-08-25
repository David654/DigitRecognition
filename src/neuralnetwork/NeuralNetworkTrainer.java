package neuralnetwork;

import gui.TrainingPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetworkTrainer
{
    private final NeuralNetwork neuralNetwork;

    public NeuralNetworkTrainer(NeuralNetwork neuralNetwork)
    {
        this.neuralNetwork = neuralNetwork;
    }

    public void learn(String imageDir, int imageSamples, int epochs)
    {
        TrainingPanel trainingPanel = new TrainingPanel(400, 600, "Neural Network Training", neuralNetwork);
        trainingPanel.setEpochsTotal(epochs);
        trainingPanel.setImageSamples(imageSamples);
        trainingPanel.open();

        trainingPanel.startTimer();
        trainingPanel.printToConsole("READING DATA...");

        File[] files = new File(imageDir).listFiles();
        double[] digits = new double[imageSamples];
        BufferedImage[] images = new BufferedImage[imageSamples];
        for(int i = 0; i < imageSamples; i++)
        {
            digits[i] = Integer.parseInt(files[i].getName().charAt(10) + "");
            try
            {
                images[i] = ImageIO.read(files[i]);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        double[][] inputNeurons = new double[imageSamples][784];

        for(int i = 0; i < inputNeurons.length; i++)
        {
            for(int x = 0; x < 28; x++)
            {
                for(int y = 0; y < 28; y++)
                {
                    inputNeurons[i][x + y * 28] = (images[i].getRGB(x, y) & 0xff) / 255.0;
                }
            }
        }

        for(int i = 1; i <= epochs; i++)
        {
            int correct = 0;
            double errorSum = 0;
            int batchSize = 100;

            for(int j = 0; j < batchSize; j++)
            {
                int imageIndex = (int) (Math.random() * imageSamples);
                double[] targets = new double[10];
                int digit = (int) digits[imageIndex];
                targets[digit] = 1.0;

                double[] out = neuralNetwork.feedForward(inputNeurons[imageIndex]);
                int maxDigit = 0;
                double maxDigitWeight = -1;

                for(int k = 0; k < 10; k++)
                {
                    if(out[k] > maxDigitWeight)
                    {
                        maxDigitWeight = out[k];
                        maxDigit = k;
                    }
                }

                if(digit == maxDigit)
                {
                    correct++;
                }

                for(int k = 0; k < 10; k++)
                {
                    errorSum += Math.pow(targets[k] - out[k], 2);
                }

                neuralNetwork.backPropagation(targets);
            }
            //System.out.println("Epoch: " + i + ", correct: " + correct + ", error: " + errorSum);
            trainingPanel.setEpochs(i);
            trainingPanel.printToConsole(("Epoch: " + i + ", correct: " + correct + ", error: " + trainingPanel.format(errorSum, "#.###")));
        }
        trainingPanel.printToConsole("LEARNING SUCCESSFULLY FINISHED.");
        trainingPanel.stopTimer();
    }

    public void saveResult(String filename)
    {
        try
        {
            File myObj = new File(filename);
            if(myObj.createNewFile()) System.out.println("File created: " + myObj.getName());
            else System.out.println("File already exists.");

            FileWriter writer = new FileWriter(filename);

            for(int i = 0; i < neuralNetwork.layers.length - 1; i++)
            {
                double[] neurons = neuralNetwork.layers[i].getNeurons();
                double[][] weights = neuralNetwork.layers[i].getWeights();
                double[] biases = neuralNetwork.layers[i].getBiases();

                //writer.write("n" + Arrays.toString(neurons));
                //writer.write("\n\n");
                writer.write("w" + Arrays.deepToString(weights));
                writer.write("\n\n");
                writer.write("b" + Arrays.toString(biases));
                writer.write("\n\n----------------------\n\n");
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch(IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void read(String filename)
    {
        ArrayList<String> lines = new ArrayList<>();
        try
        {
            Path p = Paths.get(filename);
            BufferedReader reader = Files.newBufferedReader(p);
            while(true)
            {
                String line = reader.readLine();
                if(line == null)
                {
                    break;
                }

                if(!line.equals("") && !line.equals("\n"))
                {
                    lines.add(line);
                }
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        int layerNum = 0;
        for(String line : lines)
        {
            double[][] weights = new double[neuralNetwork.layers[layerNum].getSize()][neuralNetwork.layers[layerNum].getNextSize()];
            double[] biases = new double[neuralNetwork.layers[layerNum].getSize()];

            if(line.charAt(0) == 'w')
            {
                line = line.substring(3, line.length() - 2);
                String[] values = line.split(", \\[");
                for(int i = 0; i < values.length; i++)
                {
                    String s = values[i];
                    if(s.endsWith("]"))
                    {
                        values[i] = values[i].replace("]", "");
                    }

                    String[] value = values[i].split(", ");
                    for(int j = 0; j < value.length; j++)
                    {
                        weights[i][j] = Double.parseDouble(value[j]);
                    }
                }
                neuralNetwork.layers[layerNum].setWeights(weights);
            }

            if(line.charAt(0) == 'b')
            {
                line = line.substring(2, line.length() - 1);
                String[] values = line.split(", ");
                for(int i = 0; i < values.length; i++)
                {
                    biases[i] = Double.parseDouble(values[i]);
                }
                neuralNetwork.layers[layerNum].setBiases(biases);
            }

            if(line.contains("---"))
            {
                layerNum++;
            }
        }
    }
}