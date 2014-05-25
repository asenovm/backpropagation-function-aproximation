package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Layer {

	private final List<Node> nodes;

	public Layer(final List<Node> nodes) {
		this.nodes = new LinkedList<Node>(nodes);
	}

	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public String toString() {
		return getNodes().toString();
	}

}
