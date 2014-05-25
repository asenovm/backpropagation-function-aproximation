package edu.fmi.nn.backpropagation;

import java.util.Random;

public class Edge {
	
	private double previousDelta;

	private double weight;

	private final Node end;

	public Edge(final Node start, final Node end, final double weight) {
		this(start, end);
		this.weight = weight;
	}

	public Edge(final Node start, final Node end) {
		this.end = end;

		final Random random = new Random();
		weight = random.nextDouble();
	}

	public Node getEnd() {
		return end;
	}

	public double getWeight() {
		return weight;
	}

	public void addWeight(final double delta) {
		this.weight += delta;
		previousDelta = delta;
	}

	public void addMomentum(final double momentum) {
		this.weight += momentum * previousDelta;
	}

}
