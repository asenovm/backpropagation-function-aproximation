package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A class to represent a layer in the neural network
 * 
 * @author martin
 * 
 */
public class Layer {

	private final List<Node> nodes;

	private final ActivationFunction function;

	public static enum Type {
		INPUT, HIDDEN, OUTPUT;
	}

	/**
	 * Factory method to create a new layer from the given parameters
	 * 
	 * @param nodes
	 *            the nodes that form the layer
	 * @param type
	 *            the type of the layer - can be one of the values of
	 *            {@link Type}
	 * @return the newly created layer
	 */
	public static Layer from(final List<Node> nodes, final Type type) {
		if (type == Type.HIDDEN) {
			return new Layer(nodes, new HyperbolicTanFunction());
		} else {
			return new Layer(nodes, new SigmoidFunction());
		}
	}

	private Layer(final List<Node> nodes, final ActivationFunction function) {
		this.nodes = new LinkedList<Node>(nodes);
		this.function = function;
	}

	/**
	 * Returns an unmodifiable view over the nodes in the layer
	 * 
	 * @return an unmodifiable view over the nodes in the layer
	 */
	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	/**
	 * Resets the values of the nodes in the layer
	 */
	public void reset() {
		for (final Node node : nodes) {
			node.setValue(0.0);
		}
	}

	/**
	 * Applies the activation function of the layer over the given
	 * <tt>value</tt>
	 * 
	 * @param value
	 *            the value on which the activation function is to be applied
	 * @return the activation function applied over the given <tt>value</tt>
	 */
	public double applyActivation(final double value) {
		return function.apply(value);
	}

	@Override
	public String toString() {
		return getNodes().toString();
	}

}
