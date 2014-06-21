package edu.fmi.nn.backpropagation;

/**
 * Implementations are callbacks regarding the current computation state
 * 
 * @author martin
 * 
 */
public interface ComputationCallback {

	/**
	 * A callback fired when the training of the neural network is started
	 */
	void onTrainStart();

	/**
	 * A callback fired when the training of the neural network has finished
	 */
	void onTrainEnd();
}
