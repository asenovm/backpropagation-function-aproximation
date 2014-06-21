package edu.fmi.nn.backpropagation.model;

import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.view.SimpleOnMouseListener;

/**
 * A model class to represent the user-entered points
 * 
 * @author martin
 * 
 */
public class FunctionModel extends SimpleOnMouseListener {

	private final List<PointDouble> points;

	private ModelListener listener;

	private static class SimpleModelListener implements ModelListener {

		@Override
		public void onUserPointAdded(final List<PointDouble> points) {
			// blank
		}

		@Override
		public void onApproximationReady(List<PointDouble> function,
				final double error) {
			// blank
		}

	}

	/**
	 * Creates a new model instance
	 */
	public FunctionModel() {
		points = new LinkedList<PointDouble>();
		listener = new SimpleModelListener();
	}

	/**
	 * Sets the given <tt>listener</tt> to be notified for model-related changes
	 * 
	 * @param listener
	 *            the listener that is to be notified about model-related
	 *            changes
	 */
	public void setListener(final ModelListener listener) {
		this.listener = listener;
	}

	/**
	 * Returns an unmodifiable view over the points in the model
	 * 
	 * @return an unmodifiable view over the points in the model
	 */
	public List<PointDouble> getPoints() {
		return Collections.unmodifiableList(points);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		points.add(new PointDouble(event.getX(), event.getY()));
		listener.onUserPointAdded(getPoints());
	}

	/**
	 * Resets the model to its initial state
	 */
	public void clear() {
		points.clear();
	}
}
