package edu.fmi.nn.backpropagation;

import java.util.List;

import edu.fmi.nn.backpropagation.model.PointDouble;

/**
 * Implementations are listeners for changes in the model
 * 
 * @author martin
 * 
 */
public interface ModelListener {
	/**
	 * A callback fired when a new point has been added by the user
	 * 
	 * @param model
	 *            the model object that has changed
	 */
	void onUserPointAdded(final List<PointDouble> points);

	/**
	 * A callback fired when the function, covering the input points has been
	 * approximated
	 * 
	 * @param function
	 *            the discrete representation of the given function
	 */
	void onApproximationReady(final List<PointDouble> function, final double error);
}
