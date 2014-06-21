package edu.fmi.nn.backpropagation.view;

import java.awt.Color;
import java.awt.Graphics;

import edu.fmi.nn.backpropagation.model.ScreenInfo;

/**
 * A view, representing Oxy coordinate system
 * 
 * @author martin
 * 
 */
public class CoordinateSystemView {

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
	 * Draws the view on the given graphics canvas
	 * 
	 * @param graphics
	 *            the graphics object used for painting the coordinate system
	 */
	public void draw(final Graphics graphics) {
		graphics.setColor(Color.BLACK);
		drawXAxisSeparators(graphics);
		drawYAxisSeparators(graphics);
		drawAxis(graphics);
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

}
