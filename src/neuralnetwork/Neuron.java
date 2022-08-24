package neuralnetwork;

public class Neuron
{
    private double value;

    public Neuron(double value)
    {
        this.value = value;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }
}