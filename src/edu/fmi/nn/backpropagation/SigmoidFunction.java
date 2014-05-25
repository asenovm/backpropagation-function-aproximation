package edu.fmi.nn.backpropagation;

public class SigmoidFunction implements ActivationFunction {

	@Override
	public double apply(double value) {
		return 1.0 / (1.0 + Math.exp(-value));
	}

}
