package edu.fmi.nn.backpropagation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Layer {

	public static enum Type {
		INPUT, HIDDEN, OUTPUT;
	}

	private final List<Node> nodes;

	private final Type type;

	public Layer(final List<Node> nodes, final Type type) {
		this.type = type;
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
