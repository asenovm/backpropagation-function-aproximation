package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node {

	private double previousBiasDelta;

	private double bias;

	private double value;

	private double gradient;

	private final List<Edge> edges;

	/**
	 * Creates a node that uses the given bias value
	 * 
	 * @param bias
	 *            the bias value that is to be added to the output of the node
	 */
	public Node(double bias) {
		this();
		this.bias = bias;
	}

	/**
	 * Creates a new node that has no bias value associated
	 */
	public Node() {
		edges = new LinkedList<Edge>();
	}

	/**
	 * Adds the given <tt>value</tt> to the value of the node
	 * 
	 * @param value
	 *            the value that is to be added to the neuron value
	 */
	public void addValue(final double value) {
		this.value += value;
	}

	/**
	 * Sets the neuron value to be the given <tt>value</tt>
	 * 
	 * @param value
	 *            the value that is to be set to the neuron
	 */
	public void setValue(final double value) {
		this.value = value;
	}

	/**
	 * Returns the value, associated with this neuron
	 * 
	 * @return the value, associated with this neuron
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns the bias value, associated with this neuron
	 * 
	 * @return the bias value, associated with this neuron
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * Adds the given edge as a starting one from the current node
	 * 
	 * @param edge
	 *            the edge that is to be added
	 */
	public void addEdge(final Edge edge) {
		edges.add(edge);
	}

	/**
	 * Returns an unmodifiable view over the edges starting from this node
	 * 
	 * @return an unmodifiable view over the edges starting from this node
	 */
	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}

	/**
	 * Sets the gradient value, associated with this node
	 * 
	 * @param gradient
	 *            the gradient value that is to be associated with this node
	 */
	public void setGradient(final double gradient) {
		this.gradient = gradient;
	}

	/**
	 * Returns the gradient value, associated with this node
	 * 
	 * @return the gradient value, associated with this node
	 */
	public double getGradient() {
		return gradient;
	}

	/**
	 * Adds the given bias <tt>delta</tt> to the overall bias value of the node
	 * 
	 * @param delta
	 *            the bias value that is to be added to the bias value of the
	 *            node
	 */
	public void addBias(final double delta) {
		this.bias += delta;
		previousBiasDelta = delta;
	}

	/**
	 * Adds the given <tt>momentum</tt> value to the overall momentum of the
	 * node
	 * 
	 * @param momentum
	 *            the momentum value that is to be added
	 */
	public void addMomentum(final double momentum) {
		this.bias += momentum * previousBiasDelta;
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}

}
