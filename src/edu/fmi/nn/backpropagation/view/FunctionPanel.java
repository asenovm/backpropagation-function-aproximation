package edu.fmi.nn.backpropagation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.PointDouble;

public class FunctionPanel extends JPanel implements ModelListener {

	/**
	 * {@value}
	 */
	private static final int HEIGHT_POINT = 4;

	/**
	 * {@value}
	 */
	private static final int WIDTH_POINT = 4;

	/**
	 * {@value}
	 */
	private static final int OFFSET_X_COORDINATE_SYSTEM_TEXT = 10;

	/**
	 * {@value}
	 */
	private static final int OFFSET_Y_COORDINATE_SYSTEM_TEXT = 20;

	/**
	 * {@value}
	 */
	private static final int OFFSET_SEPARATOR_COORDINATE_SYSTEM = 5;

	/**
	 * {@value}
	 */
	private static final int STEP_SEPARATOR_COORDINATE_SYSTEM = 40;

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 5458800656134374015L;

	/**
	 * {@value}
	 */
	public static final int WIDTH_FRAME = 800;

	/**
	 * {@value}
	 */
	public static final int HEIGHT_FRAME = 600;

	private final List<PointDouble> userInputPoints;

	private final List<PointDouble> points;

	public FunctionPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

		final Dimension size = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);

		points = new LinkedList<PointDouble>();
		userInputPoints = new LinkedList<PointDouble>();
	}

	public FunctionPanel() {
		this(null, false);
	}

	public FunctionPanel(boolean isDoubleBuffered) {
		this(null, isDoubleBuffered);
	}

	public FunctionPanel(LayoutManager layout) {
		this(layout, false);
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);

		graphics.setColor(Color.BLACK);

		drawAxis(graphics);
		drawXAxisSeparators(graphics);
		drawYAxisSeparators(graphics);

		graphics.setColor(Color.BLUE);
		for (final PointDouble point : userInputPoints) {
			graphics.fillOval((int) point.x, (int) point.y, WIDTH_POINT,
					HEIGHT_POINT);
		}

		final int[] xPoints = new int[points.size()];
		final int[] yPoints = new int[points.size()];

		for (int i = 0; i < points.size(); ++i) {
			final PointDouble point = points.get(i);
			xPoints[i] = (int) Math.round(point.x);
			yPoints[i] = (int) Math.round(point.y);
		}

		graphics.setColor(Color.RED);
		graphics.drawPolyline(xPoints, yPoints, points.size());
	}

	private void drawYAxisSeparators(final Graphics graphics) {
		final int ySteps = HEIGHT_FRAME / STEP_SEPARATOR_COORDINATE_SYSTEM;
		int start = ySteps / 2;
		for (int i = STEP_SEPARATOR_COORDINATE_SYSTEM; i < HEIGHT_FRAME; i += STEP_SEPARATOR_COORDINATE_SYSTEM) {
			graphics.drawLine(WIDTH_FRAME / 2
					- OFFSET_SEPARATOR_COORDINATE_SYSTEM, i, WIDTH_FRAME / 2
					+ OFFSET_SEPARATOR_COORDINATE_SYSTEM, i);
			if (start != 0) {
				graphics.drawString(Integer.toString(start), WIDTH_FRAME / 2
						- OFFSET_Y_COORDINATE_SYSTEM_TEXT, i);
			}
			--start;
		}
	}

	private void drawXAxisSeparators(Graphics graphics) {
		final int xSteps = WIDTH_FRAME / STEP_SEPARATOR_COORDINATE_SYSTEM;
		int start = -xSteps / 2 + 1;
		for (int i = STEP_SEPARATOR_COORDINATE_SYSTEM; i < WIDTH_FRAME; i += STEP_SEPARATOR_COORDINATE_SYSTEM) {
			graphics.drawLine(i, HEIGHT_FRAME / 2
					- OFFSET_SEPARATOR_COORDINATE_SYSTEM, i, HEIGHT_FRAME / 2
					+ OFFSET_SEPARATOR_COORDINATE_SYSTEM);
			graphics.drawString(Integer.toString(start), i
					- OFFSET_X_COORDINATE_SYSTEM_TEXT, HEIGHT_FRAME / 2
					+ OFFSET_Y_COORDINATE_SYSTEM_TEXT);
			++start;
		}
	}

	private void drawAxis(Graphics graphics) {
		graphics.drawLine(0, HEIGHT_FRAME / 2, WIDTH_FRAME, HEIGHT_FRAME / 2);
		graphics.drawLine(WIDTH_FRAME / 2, 0, WIDTH_FRAME / 2, HEIGHT_FRAME);
	}

	@Override
	public void onUserPointAdded(final List<PointDouble> points) {
		userInputPoints.clear();
		userInputPoints.addAll(points);
		repaint();
	}

	@Override
	public void onApproximationReady(List<PointDouble> function) {
		this.points.clear();
		this.points.addAll(function);
		repaint();
	}

}
