package edu.fmi.nn.backpropagation.model;

/**
 * A model class to represent the configuration of the neural network
 * 
 * @author martin
 * 
 */
public class NetworkConfiguration {

	private static final int DEFAULT_EPOCHS_COUNT = 1000000;

	private static final double DEFAULT_ERROR_TOLERANCE = 0.00000000000001;

	private static final double DEFAULT_LEARNING_RATE = 0.01;

	private static final double DEFAULT_MOMENTUM = 0.1;

	private static final int DEFAULT_HIDDEN_NODES_COUNT = 8;

	private int numberOfEpochs;

	private int hiddenNodesCount;

	private double learningRate;

	private double momentum;

	private double errorTolerance;

	/**
	 * Creates a new {@link NetworkConfiguration} using the default parameters
	 */
	public NetworkConfiguration() {
		this(DEFAULT_EPOCHS_COUNT, DEFAULT_HIDDEN_NODES_COUNT,
				DEFAULT_LEARNING_RATE, DEFAULT_MOMENTUM);
	}

	/**
	 * Creates a new {@link NetworkConfiguration} using the given configuration
	 * parameters
	 * 
	 * @param epochs
	 *            the number of epochs for which the training should last
	 * @param hiddenNodes
	 *            the number of hidden nodes that are to be used in the network
	 * @param learningRate
	 *            the learning rate that is to be applied
	 * @param momentum
	 *            the momentum that is to be used when computing deltas
	 */
	public NetworkConfiguration(final int epochs, final int hiddenNodes,
			final double learningRate, final double momentum) {
		this.hiddenNodesCount = hiddenNodes;
		this.momentum = momentum;
		this.learningRate = learningRate;
		this.numberOfEpochs = epochs;
		this.errorTolerance = DEFAULT_ERROR_TOLERANCE;
	}

	/**
	 * Returns the number of epochs for which the training should last
	 * 
	 * @return the number of epochs for which the training should last
	 */
	public int getEpochs() {
		return numberOfEpochs;
	}

	/**
	 * Returns the number of hidden nodes in the network
	 * 
	 * @return the number of hidden nodes in the network
	 */
	public int getHiddenNodesCount() {
		return hiddenNodesCount;
	}

	/**
	 * Returns the learning rate associated with the network
	 * 
	 * @return the learning rate associated with the network
	 */
	public double getLearningRate() {
		return learningRate;
	}

	/**
	 * Returns the momentum value associated with the network
	 * 
	 * @return the momentum value associated with the network
	 */
	public double getMomentum() {
		return momentum;
	}

	/**
	 * Returns the error tolerance associated with the network
	 * 
	 * @return the error tolerance associated with the network
	 */
	public double getTolerance() {
		return errorTolerance;
	}

}
