package edu.fmi.nn.backpropagation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.fmi.nn.backpropagation.Layer.Type;

public class NeuralNetwork {
	private int inputNodesCount;
	private int hiddenNodesCount;
	private int outputNodesCount;

	private double[] inputs;
	private double[][] inputToHiddenWeights;
	private double[] inputToHiddenSums;
	private double[] inputToHiddenBiases;
	private double[] inputToHiddenOutputs;

	private double[][] hiddenToOutputWeights;
	private double[] hiddenToOutputSums;
	private double[] hiddenToOutputBiases;
	private double[] outputs;

	private double[] outputGradents;
	private double[] hiddenGradients;

	private double[][] inputToHiddenPreviousWeightsDelta;
	private double[] inputToHiddenPreviousBiasesDelta;

	private double[][] hiddenToOutputPreviousWeightsDelta;
	private double[] hiddenToOutputPreviousBiasesDelta;

	private final Layer inputLayer;

	private final Layer hiddenLayer;

	private final Layer outputLayer;

	public NeuralNetwork(int numInput, int numHidden, int numOutput) {

		final List<Node> inputNodes = new LinkedList<Node>();
		for (int i = 0; i < numInput; ++i) {
			inputNodes.add(new Node());
		}

		final List<Node> hiddenNodes = new LinkedList<Node>();
		final double[] hiddenBiases = new double[] { 1.7, 1.8, 1.9, 2.0 };
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

		final double[] hiddenOutputWeights = new double[] { -2, -6, -1, -7,
				1.3, 1.4, 1.5, 1.6 };
		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node hiddenNode = hiddenNodes.get(i);
			for (int j = 0; j < outputNodes.size(); ++j) {
				final Node outputNode = outputNodes.get(j);
				hiddenNode.addEdge(new Edge(hiddenNode, outputNode,
						hiddenOutputWeights[i * outputNodes.size() + j]));
			}
		}

		inputLayer = new Layer(inputNodes, Type.INPUT);
		hiddenLayer = new Layer(hiddenNodes, Type.HIDDEN);
		outputLayer = new Layer(outputNodes, Type.OUTPUT);

		// this.inputNodesCount = numInput;
		// this.hiddenNodesCount = numHidden;
		// this.outputNodesCount = numOutput;
		//
		// inputs = new double[numInput];
		// inputToHiddenWeights = Helpers.MakeMatrix(numInput, numHidden);
		// inputToHiddenSums = new double[numHidden];
		// inputToHiddenBiases = new double[numHidden];
		// inputToHiddenOutputs = new double[numHidden];
		// hiddenToOutputWeights = Helpers.MakeMatrix(numHidden, numOutput);
		// hiddenToOutputSums = new double[numOutput];
		// hiddenToOutputBiases = new double[numOutput];
		// outputs = new double[numOutput];
		//
		// outputGradents = new double[numOutput];
		// hiddenGradients = new double[numHidden];
		//
		// inputToHiddenPreviousWeightsDelta = Helpers.MakeMatrix(numInput,
		// numHidden);
		// inputToHiddenPreviousBiasesDelta = new double[numHidden];
		// hiddenToOutputPreviousWeightsDelta = Helpers.MakeMatrix(numHidden,
		// numOutput);
		// hiddenToOutputPreviousBiasesDelta = new double[numOutput];
	}

	public void updateWeights(double[] targetValues, double learningRate,
			double momentum) {
		if (targetValues.length != outputNodesCount) {
			return;
		}

		for (int i = 0; i < outputGradents.length; ++i) {
			double derivative = (1 - outputs[i]) * (1 + outputs[i]);
			outputGradents[i] = derivative * (targetValues[i] - outputs[i]);
		}

		for (int i = 0; i < hiddenGradients.length; ++i) {
			double derivative = (1 - inputToHiddenOutputs[i])
					* inputToHiddenOutputs[i];
			double sum = 0.0;
			for (int j = 0; j < outputNodesCount; ++j) {
				sum += outputGradents[j] * hiddenToOutputWeights[i][j];
			}
			hiddenGradients[i] = derivative * sum;
		}

		for (int i = 0; i < inputToHiddenWeights.length; ++i) {
			for (int j = 0; j < inputToHiddenWeights[0].length; ++j) {
				double delta = learningRate * hiddenGradients[j] * inputs[i];
				inputToHiddenWeights[i][j] += delta;
				inputToHiddenWeights[i][j] += momentum
						* inputToHiddenPreviousWeightsDelta[i][j];
			}
		}

		for (int i = 0; i < inputToHiddenBiases.length; ++i) {
			double delta = learningRate * hiddenGradients[i] * 1.0;
			inputToHiddenBiases[i] += delta;
			inputToHiddenBiases[i] += momentum
					* inputToHiddenPreviousBiasesDelta[i];
		}

		for (int i = 0; i < hiddenToOutputWeights.length; ++i) {
			for (int j = 0; j < hiddenToOutputWeights[0].length; ++j) {
				double delta = learningRate * outputGradents[j]
						* inputToHiddenOutputs[i];
				hiddenToOutputWeights[i][j] += delta;
				hiddenToOutputWeights[i][j] += momentum
						* hiddenToOutputPreviousWeightsDelta[i][j];
				hiddenToOutputPreviousWeightsDelta[i][j] = delta;
			}
		}

		for (int i = 0; i < hiddenToOutputBiases.length; ++i) {
			double delta = learningRate * outputGradents[i] * 1.0;
			hiddenToOutputBiases[i] += delta;
			hiddenToOutputBiases[i] += momentum
					* hiddenToOutputPreviousBiasesDelta[i];
			hiddenToOutputPreviousBiasesDelta[i] = delta;
		}
	}

	public double[] getWeights() {
		int numWeights = (inputNodesCount * hiddenNodesCount)
				+ (hiddenNodesCount * outputNodesCount) + hiddenNodesCount
				+ outputNodesCount;
		double[] result = new double[numWeights];

		int k = 0;

		for (int i = 0; i < inputToHiddenWeights.length; ++i) {
			for (int j = 0; j < inputToHiddenWeights[0].length; ++j) {
				result[k++] = inputToHiddenWeights[i][j];
			}
		}

		for (int i = 0; i < inputToHiddenBiases.length; ++i) {
			result[k++] = inputToHiddenBiases[i];
		}

		for (int i = 0; i < hiddenToOutputWeights.length; ++i) {
			for (int j = 0; j < hiddenToOutputWeights[0].length; ++j) {
				result[k++] = hiddenToOutputWeights[i][j];
			}
		}

		for (int i = 0; i < hiddenToOutputBiases.length; ++i) {
			result[k++] = hiddenToOutputBiases[i];
		}

		return result;
	}

	public double[] computeOutputs(double[] inputValues) {
		// TODO maybe hardcode exactly 3 layers instead of using list and doing
		// this (?)

		final List<Node> inputNodes = inputLayer.getNodes();
		for (int i = 0; i < inputValues.length; ++i) {
			final Node node = inputNodes.get(i);
			node.addValue(inputValues[i]);
		}

		// TODO add bias to the equation
		for (int i = 0; i < inputNodes.size(); ++i) {
			final Node node = inputNodes.get(i);
			final List<Edge> nodeEdges = node.getEdges();

			for (final Edge edge : nodeEdges) {
				final Node end = edge.getEnd();
				end.addValue(node.getValue() * edge.getWeight());
			}
		}

		final List<Node> hiddenNodes = hiddenLayer.getNodes();
		for (int i = 0; i < hiddenNodes.size(); ++i) {
			final Node node = hiddenNodes.get(i);
			System.out.println("before bias " + node.getValue());
			System.out.println("bias is " + node.getBias());
			node.addValue(node.getBias());
			System.out.println("after bias " + node.getValue());
			node.setValue(sigmoidFunction(node.getValue()));
		}

		// for (int i = 0; i < hiddenNodes.size(); ++i) {
		// final Node node = hiddenNodes.get(i);
		// final List<Edge> nodeEdges = node.getEdges();
		//
		// for (final Edge edge : nodeEdges) {
		// final Node end = edge.getEnd();
		// end.addValue(node.getValue() * edge.getWeight());
		// }
		// }
		//
		// final List<Node> outputNodes = outputLayer.getNodes();
		// for (int i = 0; i < outputNodes.size(); ++i) {
		// final Node node = outputNodes.get(i);
		// node.addValue(node.getBias());
		// node.setValue(hyperTanFunction(node.getValue()));
		// }
		//
		// System.out
		// .println("****************************START********************");
		// System.out
		// .println("***************************INPUT*********************");
		// System.out.println(inputLayer);
		// System.out
		// .println("***************************HIDDEN*********************");
		// System.out.println(hiddenLayer);
		// System.out
		// .println("***************************OUTPUT*********************");
		// System.out.println(outputLayer);
		// System.out
		// .println("*************************END**************************");

		return null;
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
