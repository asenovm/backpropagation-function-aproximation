package edu.fmi.nn.backpropagation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.fmi.nn.backpropagation.Layer.Type;
import edu.fmi.nn.backpropagation.model.PointDouble;

public class NeuralNetwork {

	private static final int NUMBER_HIDDEN_NODES = 8;

	/**
	 * {@value}
	 */
	private static final double MOMENTUM = 0.1;

	/**
	 * {@value}
	 */
	private static final double LEARNING_RATE = 0.01;

	/**
	 * {@value}
	 */
	private static final double MAX_ERROR_TOLERANCE = 0.01;

	/**
	 * {@value}
	 */
	private static final int MAX_NUM_EPOCHS = 1000000;

	private Layer inputLayer;

	private Layer hiddenLayer;

	private Layer outputLayer;

	private void init(final List<PointDouble> points) {
		final List<Node> inputNodes = new LinkedList<Node>();
		for (int i = 0; i < points.size(); ++i) {
			inputNodes.add(new Node());
		}

		final Random random = new Random();

		final List<Node> hiddenNodes = new LinkedList<Node>();
		for (int i = 0; i < NUMBER_HIDDEN_NODES; ++i) {
			hiddenNodes.add(new Node(random.nextDouble()));
		}

		final List<Node> outputNodes = new LinkedList<Node>();
		for (int i = 0; i < points.size(); ++i) {
			outputNodes.add(new Node(random.nextDouble()));
		}

		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node inputNode = inputNodes.get(i);
			for (int j = 0; j < hiddenNodes.size(); ++j) {
				final Node hiddenNode = hiddenNodes.get(j);
				inputNode.addEdge(new Edge(inputNode, hiddenNode, random
						.nextDouble()));
			}
		}

		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node hiddenNode = hiddenNodes.get(i);
			for (int j = 0; j < outputNodes.size(); ++j) {
				final Node outputNode = outputNodes.get(j);
				hiddenNode.addEdge(new Edge(hiddenNode, outputNode, random
						.nextDouble()));
			}
		}

		inputLayer = Layer.from(inputNodes, Type.INPUT);
		hiddenLayer = Layer.from(hiddenNodes, Type.HIDDEN);
		outputLayer = Layer.from(outputNodes, Type.OUTPUT);

	}

	public void train(final List<PointDouble> points) {
		init(points);
		double[] inputValues = new double[points.size()];
		double[] targetValues = new double[points.size()];

		for (int i = 0; i < points.size(); ++i) {
			inputValues[i] = points.get(i).x;
			targetValues[i] = points.get(i).y;
		}

		int epochsCount = 0;
		double[] yValues = computeOutputs(inputValues);

		while (isTraining(targetValues, epochsCount, yValues)) {
			updateWeights(targetValues);
			yValues = computeOutputs(inputValues);
			++epochsCount;
		}
		System.out
				.println("target values are " + Arrays.toString(targetValues));
		System.out.println("real values are " + Arrays.toString(yValues));
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

	public double[] computeOutputs(double[] inputValues) {
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
