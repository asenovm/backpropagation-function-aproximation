package edu.fmi.nn.backpropagation.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JFrame;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.PointsListener;
import edu.fmi.nn.backpropagation.model.FunctionModel;

public class FunctionView extends JFrame implements ModelListener {

	private static final int STEP_X_COORDINATE_SYSTEM = 40;

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
	private static final int WIDTH_FRAME = 800;

	/**
	 * {@value}
	 */
	private static final int HEIGHT_FRAME = 600;

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -4178865610705123099L;

	public static class SimplePointsListener implements PointsListener {

		@Override
		public void onPointAdded(int x, int y) {
			// blank
		}

	}

	public FunctionView() throws HeadlessException {
		this("", null);
	}

	public FunctionView(GraphicsConfiguration gc) {
		this("", gc);
	}

	public FunctionView(String title) throws HeadlessException {
		this(title, null);
	}

	public FunctionView(String title, GraphicsConfiguration gc) {
		super(title, gc);

		final Dimension size = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setVisible(true);
	}

	@Override
	public void onModelChanged(FunctionModel model) {
		final Graphics graphics = getGraphics();
		for (final Point point : model.getPoints()) {
			graphics.fillRect(point.x, point.y, WIDTH_POINT, HEIGHT_POINT);
		}
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);

		drawAxis(graphics);
		drawXAxisSeparators(graphics);
	}

	private void drawXAxisSeparators(Graphics graphics) {
		final int xSteps = WIDTH_FRAME / STEP_X_COORDINATE_SYSTEM;
		int start = -xSteps / 2 + 1;
		for (int i = STEP_X_COORDINATE_SYSTEM; i < WIDTH_FRAME; i += STEP_X_COORDINATE_SYSTEM) {
			graphics.drawLine(i, HEIGHT_FRAME / 2 - 5, i, HEIGHT_FRAME / 2 + 5);
			graphics.drawString(Integer.toString(start), i - 10,
					HEIGHT_FRAME / 2 + 20);
			++start;
		}
	}

	private void drawAxis(Graphics graphics) {
		graphics.drawLine(0, HEIGHT_FRAME / 2, WIDTH_FRAME, HEIGHT_FRAME / 2);
		graphics.drawLine(WIDTH_FRAME / 2, 0, WIDTH_FRAME / 2, HEIGHT_FRAME);
	}
}
