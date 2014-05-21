package edu.fmi.nn.backpropagation.model;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.fmi.nn.backpropagation.ModelListener;

public class FunctionModel implements MouseListener {

	private final List<Point> points;

	private ModelListener listener;

	private static class SimpleModelListener implements ModelListener {

		@Override
		public void onModelChanged(FunctionModel model) {
			// blank
		}

	}

	public FunctionModel() {
		points = new LinkedList<Point>();
		listener = new SimpleModelListener();
	}

	public void setListener(final ModelListener listener) {
		this.listener = listener;
	}

	public List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		points.add(new Point(event.getX(), event.getY()));
		listener.onModelChanged(this);
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
}
