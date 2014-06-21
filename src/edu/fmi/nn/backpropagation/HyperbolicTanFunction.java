package edu.fmi.nn.backpropagation;

/**
 * An implementation of the {@link ActivationFunction} interface using tanh
 * function
 * 
 * @author martin
 * 
 * @see ActivationFunction
 * 
 */
public class HyperbolicTanFunction implements ActivationFunction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double apply(double value) {
		return Math.tanh(value);
	}

}
