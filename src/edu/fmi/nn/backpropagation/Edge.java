package edu.fmi.nn.backpropagation;

import java.util.Random;

/**
 * Class, representing a connection in the neural network
 * 
 * @author martin
 * 
 */
public class Edge {

	private double previousDelta;

	private double weight;

	private final Node end;

	/**
	 * Creates a new edge from the parameters given
	 * 
	 * @param start
	 *            the node that is the beginning of the edge
	 * @param end
	 *            the node that is the end of the edge
	 * @param weight
	 *            the weight that is associated with the edge
	 */
	public Edge(final Node start, final Node end, final double weight) {
		this(start, end);
		this.weight = weight;
	}

	/**
	 * Creates a new edge from the given parameters using a random value as an
	 * edge weight
	 * 
	 * @param start
	 *            the node that is the beginning of the edge
	 * @param end
	 *            the node that is the end of the edge
	 */
	public Edge(final Node start, final Node end) {
		this.end = end;

		final Random random = new Random();
		weight = random.nextDouble();
	}

	/**
	 * Returns the end node of this edge
	 * 
	 * @return the end node of this edge
	 */
	public Node getEnd() {
		return end;
	}

	/**
	 * Returns the weight, associated with this edge
	 * 
	 * @return the weight, associated with this edge
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Adds the given <tt>delta</tt> to the current edge weight
	 * 
	 * @param delta
	 *            the value that is to be added to the current edge weight
	 */
	public void addWeight(final double delta) {
		this.weight += delta;
		previousDelta = delta;
	}

	/**
	 * Adds the given <tt>momentum</tt> to the current edge weight
	 * 
	 * @param momentum
	 *            the value that is to be added to the current edge weight
	 */
	public void addMomentum(final double momentum) {
		this.weight += momentum * previousDelta;
	}

}
