package edu.fmi.nn.backpropagation;

public class HyperbolicTanFunction implements ActivationFunction {

	@Override
	public double apply(double value) {
		if (value < -10.0)
			return -1.0;
		else if (value > 10.0)
			return 1.0;
		else
			return Math.tanh(value);
	}

}
