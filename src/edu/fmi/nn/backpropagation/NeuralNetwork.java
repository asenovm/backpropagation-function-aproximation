package edu.fmi.nn.backpropagation;

import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {
	private final Layer inputLayer;

	private final Layer hiddenLayer;

	private final Layer outputLayer;

	public NeuralNetwork(int numInput, int numHidden, int numOutput) {

		final List<Node> inputNodes = new LinkedList<Node>();
		for (int i = 0; i < numInput; ++i) {
			inputNodes.add(new Node());
		}

		final List<Node> hiddenNodes = new LinkedList<Node>();
		final double[] hiddenBiases = new double[] { -2.0, -6.0, -1, -7 };
		for (int i = 0; i < numHidden; ++i) {
			hiddenNodes.add(new Node(hiddenBiases[i]));
		}

		final List<Node> outputNodes = new LinkedList<Node>();
		final double[] outputBiases = new double[] { -2.5, -5.0 };
		for (int i = 0; i < numOutput; ++i) {
			outputNodes.add(new Node(outputBiases[i]));
		}

		final double[] inputHiddenWeights = new double[] { 0.1, 0.2, 0.3, 0.4,
				0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2 };
		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node inputNode = inputNodes.get(i);
			for (int j = 0; j < hiddenNodes.size(); ++j) {
				final Node hiddenNode = hiddenNodes.get(j);
				inputNode.addEdge(new Edge(inputNode, hiddenNode,
						inputHiddenWeights[i * hiddenNodes.size() + j]));
			}
		}

		final double[] hiddenOutputWeights = new double[] { 1.3, 1.4, 1.5, 1.6,
				1.7, 1.8, 1.9, 2.0 };
		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node hiddenNode = hiddenNodes.get(i);
			for (int j = 0; j < outputNodes.size(); ++j) {
				final Node outputNode = outputNodes.get(j);
				hiddenNode.addEdge(new Edge(hiddenNode, outputNode,
						hiddenOutputWeights[i * outputNodes.size() + j]));
			}
		}

		inputLayer = new Layer(inputNodes);
		hiddenLayer = new Layer(hiddenNodes);
		outputLayer = new Layer(outputNodes);
	}

	public void updateWeights(double[] targetValues, double learningRate,
			double momentum) {

		final List<Node> inputNodes = inputLayer.getNodes();
		final List<Node> outputNodes = outputLayer.getNodes();
		final List<Node> hiddenNodes = hiddenLayer.getNodes();

		updateGradients(targetValues, outputNodes, hiddenNodes);

		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node node = inputNodes.get(i);
			final List<Edge> edges = node.getEdges();
			for (final Edge edge : edges) {
				final Node end = edge.getEnd();
				double delta = learningRate * end.getGradient()
						* node.getValue();
				edge.addMomentum(momentum);
				edge.addWeight(delta);
			}
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			final List<Edge> edges = node.getEdges();
			for (final Edge edge : edges) {
				final Node end = edge.getEnd();
				double delta = learningRate * end.getGradient()
						* node.getValue();
				edge.addMomentum(momentum);
				edge.addWeight(delta);
			}
		}

		updateBiases(learningRate, momentum, outputNodes, hiddenNodes);
	}

	private void updateGradients(double[] targetValues,
			final List<Node> outputNodes, final List<Node> hiddenNodes) {
		for (int i = 0; i < outputNodes.size(); ++i) {
			final Node node = outputNodes.get(i);

			final double derivative = (1 - node.getValue())
					* (1 + node.getValue());
			final double gradient = derivative
					* (targetValues[i] - node.getValue());
			node.setGradient(gradient);
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			double derivative = (1 - node.getValue()) * node.getValue();
			double sum = 0.0;
			for (final Edge edge : node.getEdges()) {
				final Node outputNode = edge.getEnd();
				sum += outputNode.getGradient() * edge.getWeight();
			}
			node.setGradient(derivative * sum);
		}
	}

	private void updateBiases(double learningRate, double momentum,
			final List<Node> outputNodes, final List<Node> hiddenNodes) {
		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			double delta = learningRate * node.getGradient();
			node.addMomentum(momentum);
			node.addBias(delta);
		}

		for (int i = 0; i < outputNodes.size(); ++i) {
			final Node node = outputNodes.get(i);
			double delta = learningRate * node.getGradient();
			node.addMomentum(momentum);
			node.addBias(delta);
		}
	}

	public double[] computeOutputs(double[] inputValues) {
		final List<Node> inputNodes = inputLayer.getNodes();
		final List<Node> hiddenNodes = hiddenLayer.getNodes();
		final List<Node> outputNodes = outputLayer.getNodes();

		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node node = inputNodes.get(i);
			node.setValue(0.0);
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			node.setValue(0.0);
		}

		for (int i = 0; i < outputNodes.size(); ++i) {
			final Node node = outputNodes.get(i);
			node.setValue(0.0);
		}

		for (int i = 0; i < inputValues.length; ++i) {
			final Node node = inputNodes.get(i);
			node.addValue(inputValues[i]);
		}

		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node node = inputNodes.get(i);
			final List<Edge> nodeEdges = node.getEdges();

			for (final Edge edge : nodeEdges) {
				final Node end = edge.getEnd();
				end.addValue(node.getValue() * edge.getWeight());
			}
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			node.addValue(node.getBias());
			node.setValue(sigmoidFunction(node.getValue()));
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			final List<Edge> nodeEdges = node.getEdges();

			for (final Edge edge : nodeEdges) {
				final Node end = edge.getEnd();
				end.addValue(node.getValue() * edge.getWeight());
			}
		}

		for (int i = 0; i < outputNodes.size(); ++i) {
			final Node node = outputNodes.get(i);
			node.addValue(node.getBias());
			node.setValue(hyperTanFunction(node.getValue()));
		}

		final double[] result = new double[outputNodes.size()];
		for (int i = 0; i < outputNodes.size(); ++i) {
			result[i] = outputNodes.get(i).getValue();
		}

		return result;
	}

	private static double sigmoidFunction(double x) {
		if (x < -45.0)
			return 0.0;
		else if (x > 45.0)
			return 1.0;
		else
			return 1.0 / (1.0 + Math.exp(-x));
	}

	private static double hyperTanFunction(double x) {
		if (x < -10.0)
			return -1.0;
		else if (x > 10.0)
			return 1.0;
		else
			return Math.tanh(x);
	}
}
