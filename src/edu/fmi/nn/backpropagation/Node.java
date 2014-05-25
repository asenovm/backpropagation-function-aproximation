package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node {

	double bias;

	double value;

	private final List<Edge> edges;
	
	public Node(double bias) {
		this();
		this.bias = bias;
	}

	public Node() {
		edges = new LinkedList<Edge>();
	}

	public void addValue(final double value) {
		this.value += value;
	}

	public void setValue(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(final double bias) {
		this.bias = bias;
	}

	public void addEdge(final Edge edge) {
		edges.add(edge);
	}

	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}

}
