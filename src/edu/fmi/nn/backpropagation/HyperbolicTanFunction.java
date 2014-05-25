package edu.fmi.nn.backpropagation;

public class HyperbolicTanFunction implements ActivationFunction {

	@Override
	public double apply(double value) {
		return Math.tanh(value);
	}

}
