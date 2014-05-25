package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Layer {

	private final List<Node> nodes;

	private final ActivationFunction function;

	public static enum Type {
		INPUT, HIDDEN, OUTPUT;
	}

	public static Layer from(final List<Node> nodes, final Type type) {
		if (type == Type.HIDDEN) {
			return new Layer(nodes, new SigmoidFunction());
		} else {
			return new Layer(nodes, new HyperbolicTanFunction());
		}
	}

	private Layer(final List<Node> nodes, final ActivationFunction function) {
		this.nodes = new LinkedList<Node>(nodes);
		this.function = function;
	}

	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	public void reset() {
		for (final Node node : nodes) {
			node.setValue(0.0);
		}
	}

	@Override
	public String toString() {
		return getNodes().toString();
	}

}
