package edu.fmi.nn.backpropagation.model;

public class NetworkConfiguration {

	private static final int DEFAULT_EPOCHS_COUNT = 1000000;

	private static final double DEFAULT_ERROR_TOLERANCE = 0.00000001;

	private static final double DEFAULT_LEARNING_RATE = 0.01;

	private static final double DEFAULT_MOMENTUM = 0.1;

	private static final int DEFAULT_HIDDEN_NODES_COUNT = 8;

	private int numberOfEpochs;

	private int hiddenNodesCount;

	private double learningRate;

	private double momentum;

	private double errorTolerance;

	public NetworkConfiguration() {
		this(DEFAULT_EPOCHS_COUNT, DEFAULT_HIDDEN_NODES_COUNT,
				DEFAULT_LEARNING_RATE, DEFAULT_MOMENTUM);
	}

	public NetworkConfiguration(final int epochs, final int hiddenNodes,
			final double learningRate, final double momentum) {
		setHiddenNodesCount(hiddenNodes);
		setMomentum(momentum);
		setLearningRate(learningRate);
		setEpochs(epochs);
		setErrorTolerance(DEFAULT_ERROR_TOLERANCE);
	}

	public int getEpochs() {
		return numberOfEpochs;
	}

	public void setEpochs(int numberOfEpochs) {
		this.numberOfEpochs = numberOfEpochs;
	}

	public int getHiddenNodesCount() {
		return hiddenNodesCount;
	}

	public void setHiddenNodesCount(int hiddenNodesCount) {
		this.hiddenNodesCount = hiddenNodesCount;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public double getTolerance() {
		return errorTolerance;
	}

	public void setErrorTolerance(double errorTolerance) {
		this.errorTolerance = errorTolerance;
	}

}
