package edu.fmi.nn.backpropagation;

/**
 * Implementations represent activation functions that are used to compute the
 * output of neurons
 * 
 * @author martin
 * 
 */
public interface ActivationFunction {
	/**
	 * Applies the respective activation function on the <tt>value</tt> given
	 * 
	 * @param value
	 *            the value on which the activation function is to be applied
	 * @return the computed output of the activation function over the given
	 *         <tt>value</tt>
	 */
	double apply(double value);
}
