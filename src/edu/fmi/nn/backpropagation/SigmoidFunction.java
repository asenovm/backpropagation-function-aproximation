package edu.fmi.nn.backpropagation;

public class SigmoidFunction implements ActivationFunction {

	@Override
	public double apply(double value) {
		if (value < -45.0)
			return 0.0;
		else if (value > 45.0)
			return 1.0;
		else
			return 1.0 / (1.0 + Math.exp(-value));
	}

}
