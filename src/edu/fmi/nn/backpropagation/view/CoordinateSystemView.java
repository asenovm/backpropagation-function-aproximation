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
	private static final int OFFSET_SEPARATOR = 5;

	/**
	 * Draws the view on the given graphics canvas
	 * 
	 * @param graphics
	 *            the graphics object used for painting the coordinate system
	 */
	public void draw(final Graphics graphics) {
		graphics.setColor(Color.BLACK);
		drawAxis(graphics);
		drawXAxisSeparators(graphics);
		drawYAxisSeparators(graphics);

		graphics.drawString("0", ScreenInfo.WIDTH / 2 + OFFSET_SEPARATOR,
				ScreenInfo.HEIGHT_PANE / 2 - OFFSET_SEPARATOR);
	}

	private void drawYAxisSeparators(final Graphics graphics) {
		graphics.drawLine(ScreenInfo.WIDTH / 2 - OFFSET_SEPARATOR,
				OFFSET_SEPARATOR, ScreenInfo.WIDTH / 2 + OFFSET_SEPARATOR,
				OFFSET_SEPARATOR);
		graphics.drawLine(ScreenInfo.WIDTH / 2 - OFFSET_SEPARATOR,
				ScreenInfo.HEIGHT_PANE - OFFSET_SEPARATOR, ScreenInfo.WIDTH / 2
						+ OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE
						- OFFSET_SEPARATOR);

		graphics.drawString("-1", ScreenInfo.WIDTH / 2 + 2 * OFFSET_SEPARATOR,
				ScreenInfo.HEIGHT_PANE - OFFSET_SEPARATOR);
		graphics.drawString("+1", ScreenInfo.WIDTH / 2 + 2 * OFFSET_SEPARATOR,
				3 * OFFSET_SEPARATOR);
	}

	private void drawXAxisSeparators(Graphics graphics) {
		graphics.drawLine(OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE / 2
				- OFFSET_SEPARATOR, OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE
				/ 2 + OFFSET_SEPARATOR);
		graphics.drawLine(ScreenInfo.WIDTH - OFFSET_SEPARATOR,
				ScreenInfo.HEIGHT_PANE / 2 - OFFSET_SEPARATOR, ScreenInfo.WIDTH
						- OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE / 2
						+ OFFSET_SEPARATOR);

		graphics.drawString("-1", 0, ScreenInfo.HEIGHT_PANE / 2 + 4
				* OFFSET_SEPARATOR);
		graphics.drawString("+1", ScreenInfo.WIDTH - 4 * OFFSET_SEPARATOR,
				ScreenInfo.HEIGHT_PANE / 2 + 4 * OFFSET_SEPARATOR);
	}

	private void drawAxis(Graphics graphics) {
		graphics.drawLine(OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE / 2,
				ScreenInfo.WIDTH - OFFSET_SEPARATOR, ScreenInfo.HEIGHT_PANE / 2);
		graphics.drawLine(ScreenInfo.WIDTH / 2, OFFSET_SEPARATOR,
				ScreenInfo.WIDTH / 2, ScreenInfo.HEIGHT_PANE - OFFSET_SEPARATOR);
	}

}
