package edu.fmi.nn.backpropagation;

public class Edge {

	private double weight;

	private double bias;

	private final Node start;

	private final Node end;

	public Edge(final Node start, final Node end) {
		this.start = start;
		this.end = end;
	}
}
