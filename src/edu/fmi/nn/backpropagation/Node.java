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
		previousBiasDelta = 0;
	}

	public void addEdge(final Edge edge) {
		edges.add(edge);
	}

	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}

	public void setGradient(final double gradient) {
		this.gradient = gradient;
	}

	public double getGradient() {
		return gradient;
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}

	public void addBias(final double delta) {
		this.bias += delta;
		previousBiasDelta = delta;
	}

	public double getPrevBiasDelta() {
		return previousBiasDelta;
	}

	public void addMomentum(final double momentum) {
		this.bias += momentum * previousBiasDelta;
	}
	
}
