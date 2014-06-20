package edu.fmi.nn.backpropagation.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.fmi.nn.backpropagation.ModelListener;

public class FunctionModel implements MouseListener {

	private final List<PointDouble> points;

	private ModelListener listener;

	private static class SimpleModelListener implements ModelListener {

		@Override
		public void onUserPointAdded(final List<PointDouble> points) {
			// blank
		}

		@Override
		public void onApproximationReady(List<PointDouble> function) {
			// blank
		}

	}

	public FunctionModel() {
		points = new LinkedList<PointDouble>();
		listener = new SimpleModelListener();
	}

	public void setListener(final ModelListener listener) {
		this.listener = listener;
	}

	public List<PointDouble> getPoints() {
		return Collections.unmodifiableList(points);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		points.add(new PointDouble(event.getX(), event.getY()));
		listener.onUserPointAdded(getPoints());
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// blank
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// blank
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// blank
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// blank
	}

	public void clear() {
		points.clear();
	}
}
