package edu.fmi.nn.backpropagation;

import java.util.Arrays;

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

	public NeuralNetwork(int numInput, int numHidden, int numOutput) {
		this.inputNodesCount = numInput;
		this.hiddenNodesCount = numHidden;
		this.outputNodesCount = numOutput;

		inputs = new double[numInput];
		inputToHiddenWeights = Helpers.MakeMatrix(numInput, numHidden);
		inputToHiddenSums = new double[numHidden];
		inputToHiddenBiases = new double[numHidden];
		inputToHiddenOutputs = new double[numHidden];
		hiddenToOutputWeights = Helpers.MakeMatrix(numHidden, numOutput);
		hiddenToOutputSums = new double[numOutput];
		hiddenToOutputBiases = new double[numOutput];
		outputs = new double[numOutput];

		outputGradents = new double[numOutput];
		hiddenGradients = new double[numHidden];

		inputToHiddenPreviousWeightsDelta = Helpers.MakeMatrix(numInput,
				numHidden);
		inputToHiddenPreviousBiasesDelta = new double[numHidden];
		hiddenToOutputPreviousWeightsDelta = Helpers.MakeMatrix(numHidden,
				numOutput);
		hiddenToOutputPreviousBiasesDelta = new double[numOutput];
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

	public void setWeights(double[] weights) {
		int numWeights = (inputNodesCount * hiddenNodesCount)
				+ (hiddenNodesCount * outputNodesCount) + hiddenNodesCount
				+ outputNodesCount;

		if (weights.length != numWeights) {
			return;
		}

		int k = 0;
		for (int i = 0; i < inputNodesCount; ++i) {
			for (int j = 0; j < hiddenNodesCount; ++j) {
				inputToHiddenWeights[i][j] = weights[k++];
			}
		}

		for (int i = 0; i < hiddenNodesCount; ++i) {
			inputToHiddenBiases[i] = weights[k++];
		}

		for (int i = 0; i < hiddenNodesCount; ++i) {
			for (int j = 0; j < outputNodesCount; ++j) {
				hiddenToOutputWeights[i][j] = weights[k++];
			}
		}

		for (int i = 0; i < outputNodesCount; ++i) {
			hiddenToOutputBiases[i] = weights[k++];
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
		if (inputValues.length != inputNodesCount) {
			return new double[0];
		}

		Arrays.fill(inputToHiddenSums, 0.0);
		Arrays.fill(hiddenToOutputSums, 0.0);
		inputs = Arrays.copyOf(inputValues, inputValues.length);

		for (int j = 0; j < hiddenNodesCount; ++j) {
			for (int i = 0; i < inputNodesCount; ++i) {
				inputToHiddenSums[j] += this.inputs[i]
						* inputToHiddenWeights[i][j];
			}
		}

		for (int i = 0; i < hiddenNodesCount; ++i) {
			inputToHiddenSums[i] += inputToHiddenBiases[i];
		}

		for (int i = 0; i < hiddenNodesCount; ++i) {
			inputToHiddenOutputs[i] = sigmoidFunction(inputToHiddenSums[i]);
		}

		for (int j = 0; j < outputNodesCount; ++j) {
			for (int i = 0; i < hiddenNodesCount; ++i) {
				hiddenToOutputSums[j] += inputToHiddenOutputs[i]
						* hiddenToOutputWeights[i][j];
			}
		}

		for (int i = 0; i < outputNodesCount; ++i) {
			hiddenToOutputSums[i] += hiddenToOutputBiases[i];
		}

		for (int i = 0; i < outputNodesCount; ++i) {
			this.outputs[i] = hyperTanFunction(hiddenToOutputSums[i]);
		}

		double[] result = new double[outputNodesCount];
		result = Arrays.copyOf(outputs, outputs.length);

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
