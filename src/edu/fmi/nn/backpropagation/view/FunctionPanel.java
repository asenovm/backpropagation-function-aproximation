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
import edu.fmi.nn.backpropagation.model.ScreenInfo;

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

	private final List<PointDouble> userInputPoints;

	private final List<PointDouble> points;

	public FunctionPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

		final Dimension size = new Dimension(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT_PANE);
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
		final int ySteps = ScreenInfo.HEIGHT_PANE
				/ STEP_SEPARATOR_COORDINATE_SYSTEM;
		int start = ySteps / 2;
		for (int i = STEP_SEPARATOR_COORDINATE_SYSTEM; i < ScreenInfo.HEIGHT_PANE; i += STEP_SEPARATOR_COORDINATE_SYSTEM) {
			graphics.drawLine(ScreenInfo.WIDTH / 2
					- OFFSET_SEPARATOR_COORDINATE_SYSTEM, i, ScreenInfo.WIDTH
					/ 2 + OFFSET_SEPARATOR_COORDINATE_SYSTEM, i);
			if (start != 0) {
				graphics.drawString(Integer.toString(start), ScreenInfo.WIDTH
						/ 2 - OFFSET_Y_COORDINATE_SYSTEM_TEXT, i);
			}
			--start;
		}
	}

	private void drawXAxisSeparators(Graphics graphics) {
		final int xSteps = ScreenInfo.WIDTH / STEP_SEPARATOR_COORDINATE_SYSTEM;
		int start = -xSteps / 2 + 1;
		for (int i = STEP_SEPARATOR_COORDINATE_SYSTEM; i < ScreenInfo.WIDTH; i += STEP_SEPARATOR_COORDINATE_SYSTEM) {
			graphics.drawLine(i, ScreenInfo.HEIGHT_PANE / 2
					- OFFSET_SEPARATOR_COORDINATE_SYSTEM, i,
					ScreenInfo.HEIGHT_PANE / 2
							+ OFFSET_SEPARATOR_COORDINATE_SYSTEM);
			graphics.drawString(Integer.toString(start), i
					- OFFSET_X_COORDINATE_SYSTEM_TEXT, ScreenInfo.HEIGHT_PANE
					/ 2 + OFFSET_Y_COORDINATE_SYSTEM_TEXT);
			++start;
		}
	}

	private void drawAxis(Graphics graphics) {
		graphics.drawLine(0, ScreenInfo.HEIGHT_PANE / 2, ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT_PANE / 2);
		graphics.drawLine(ScreenInfo.WIDTH / 2, 0, ScreenInfo.WIDTH / 2,
				ScreenInfo.HEIGHT_PANE);
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
