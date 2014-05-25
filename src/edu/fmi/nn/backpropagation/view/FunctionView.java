package edu.fmi.nn.backpropagation.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JFrame;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.FunctionModel;

public class FunctionView extends JFrame implements ModelListener {

	private static final int OFFSET_X_COORDINATE_SYSTEM_TEXT = 10;

	private static final int OFFSET_Y_COORDINATE_SYSTEM_TEXT = 20;

	private static final int OFFSET_COORDINATE_SYSTEM_SEPARATOR = 5;

	private static final int STEP_COORDINATE_SYSTEM_SEPARATOR = 40;

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
		drawYAxisSeparators(graphics);
	}

	private void drawYAxisSeparators(final Graphics graphics) {
		final int ySteps = HEIGHT_FRAME / STEP_COORDINATE_SYSTEM_SEPARATOR;
		int start = ySteps / 2;
		for (int i = STEP_COORDINATE_SYSTEM_SEPARATOR; i < HEIGHT_FRAME; i += STEP_COORDINATE_SYSTEM_SEPARATOR) {
			graphics.drawLine(WIDTH_FRAME / 2
					- OFFSET_COORDINATE_SYSTEM_SEPARATOR, i, WIDTH_FRAME / 2
					+ OFFSET_COORDINATE_SYSTEM_SEPARATOR, i);
			if (start != 0) {
				graphics.drawString(Integer.toString(start), WIDTH_FRAME / 2
						- OFFSET_Y_COORDINATE_SYSTEM_TEXT, i);
			}
			--start;
		}
	}

	private void drawXAxisSeparators(Graphics graphics) {
		final int xSteps = WIDTH_FRAME / STEP_COORDINATE_SYSTEM_SEPARATOR;
		int start = -xSteps / 2 + 1;
		for (int i = STEP_COORDINATE_SYSTEM_SEPARATOR; i < WIDTH_FRAME; i += STEP_COORDINATE_SYSTEM_SEPARATOR) {
			graphics.drawLine(i, HEIGHT_FRAME / 2
					- OFFSET_COORDINATE_SYSTEM_SEPARATOR, i, HEIGHT_FRAME / 2
					+ OFFSET_COORDINATE_SYSTEM_SEPARATOR);
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
}
