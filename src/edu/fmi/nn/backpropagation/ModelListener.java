package edu.fmi.nn.backpropagation;

import edu.fmi.nn.backpropagation.model.FunctionModel;

/**
 * Implementations are listeners for changes in the model
 * 
 * @author martin
 * 
 */
public interface ModelListener {
	/**
	 * A callback fired when a change to the model has been made
	 * 
	 * @param model
	 *            the model object that has changed
	 */
	void onModelChanged(final FunctionModel model);
}
