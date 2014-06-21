package edu.fmi.nn.backpropagation;

/**
 * An implementation of the {@link ActivationFunction} interface using the
 * sigmoid function
 * 
 * @author martin
 * @see ActivationFunction
 * 
 */
public class SigmoidFunction implements ActivationFunction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double apply(double value) {
		return (1.0 - Math.exp(-value)) / (1.0 + Math.exp(-value));
	}

}
