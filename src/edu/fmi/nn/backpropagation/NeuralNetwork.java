package edu.fmi.nn.backpropagation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.fmi.nn.backpropagation.Layer.Type;

public class NeuralNetwork {

	/**
	 * {@value}
	 */
	private static final double MOMENTUM = 0.04;

	/**
	 * {@value}
	 */
	private static final double LEARNING_RATE = 0.90;

	/**
	 * {@value}
	 */
	private static final double MAX_ERROR_TOLERANCE = 0.01;

	/**
	 * {@value}
	 */
	private static final int MAX_NUM_EPOCHS = 1000;

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

		inputLayer = Layer.from(inputNodes, Type.INPUT);
		hiddenLayer = Layer.from(hiddenNodes, Type.HIDDEN);
		outputLayer = Layer.from(outputNodes, Type.OUTPUT);
	}

	public void train() {
		double[] inputValues = new double[] { 1.0, 2.0, 3.0 };
		double[] targetValues = new double[] { -0.8500, 0.7500 };

		int epochsCount = 0;
		double[] yValues = computeOutputs(inputValues);

		while (isTraining(targetValues, epochsCount, yValues)) {
			updateWeights(targetValues);
			yValues = computeOutputs(inputValues);
			System.out.println(Arrays.toString(yValues));
			++epochsCount;
		}
	}

	private boolean isTraining(double[] targetValues, int epochsCount,
			double[] yValues) {
		final double error = getError(targetValues, yValues);
		return epochsCount < MAX_NUM_EPOCHS && error > MAX_ERROR_TOLERANCE;
	}

	private double getError(double[] target, double[] output) {
		double sum = 0.0;
		for (int i = 0; i < target.length; ++i) {
			sum += Math.abs(target[i] - output[i]);
		}
		return sum;
	}

	private void updateWeights(double[] targetValues) {

		final List<Node> outputNodes = outputLayer.getNodes();
		final List<Node> hiddenNodes = hiddenLayer.getNodes();

		updateGradients(targetValues, outputNodes, hiddenNodes);

		updateWeights(inputLayer);
		updateWeights(hiddenLayer);

		updateBiases(hiddenNodes);
		updateBiases(outputNodes);
	}

	private void updateWeights(final Layer layer) {
		final List<Node> nodes = layer.getNodes();
		for (int i = 0; i < nodes.size(); ++i) {
			final Node node = nodes.get(i);
			final List<Edge> edges = node.getEdges();
			for (final Edge edge : edges) {
				final Node end = edge.getEnd();
				double delta = LEARNING_RATE * end.getGradient()
						* node.getValue();
				edge.addMomentum(MOMENTUM);
				edge.addWeight(delta);
			}
		}
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

	private void updateBiases(final List<Node> nodes) {
		for (int i = 0; i < nodes.size(); ++i) {
			final Node node = nodes.get(i);
			double delta = LEARNING_RATE * node.getGradient();
			node.addMomentum(MOMENTUM);
			node.addBias(delta);
		}
	}

	private double[] computeOutputs(double[] inputValues) {
		final List<Node> inputNodes = inputLayer.getNodes();
		final List<Node> outputNodes = outputLayer.getNodes();

		inputLayer.reset();
		hiddenLayer.reset();
		outputLayer.reset();

		for (int i = 0; i < inputValues.length; ++i) {
			final Node node = inputNodes.get(i);
			node.addValue(inputValues[i]);
		}

		computeOutput(inputLayer, hiddenLayer);
		computeOutput(hiddenLayer, outputLayer);

		final double[] result = new double[outputNodes.size()];
		for (int i = 0; i < outputNodes.size(); ++i) {
			result[i] = outputNodes.get(i).getValue();
		}

		return result;
	}

	private void computeOutput(final Layer currentLayer, final Layer nextLayer) {
		final List<Node> currentLayerNodes = currentLayer.getNodes();
		final List<Node> nextLayerNodes = nextLayer.getNodes();

		for (int i = 0; i < currentLayerNodes.size(); ++i) {
			final Node node = currentLayerNodes.get(i);
			final List<Edge> nodeEdges = node.getEdges();

			for (final Edge edge : nodeEdges) {
				final Node end = edge.getEnd();
				end.addValue(node.getValue() * edge.getWeight());
			}
		}

		for (int i = 0; i < nextLayerNodes.size(); ++i) {
			final Node node = nextLayerNodes.get(i);
			node.addValue(node.getBias());
			node.setValue(nextLayer.applyActivation(node.getValue()));
		}
	}

}
