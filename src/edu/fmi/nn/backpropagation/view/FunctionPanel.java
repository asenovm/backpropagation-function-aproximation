package edu.fmi.nn.backpropagation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.ComputationCallback;
import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;

public class FunctionPanel extends JPanel implements ModelListener,
		ComputationCallback {

	/**
	 * {@value}
	 */
	private static final int HEIGHT_POINT = 6;

	/**
	 * {@value}
	 */
	private static final int WIDTH_POINT = 6;

	/**
	 * {@value}
	 */
	private static final int TEXT_WAIT_SIZE = 26;

	/**
	 * {@value}
	 */
	private static final String TEXT_WAIT = "Please wait for the output to be computed";

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 5458800656134374015L;

	private final List<PointDouble> userInputPoints;

	private final List<PointDouble> points;

	private final CoordinateSystemView coordinateSystem;

	private final Runnable repaintRunnable;

	private boolean isTraining;

	private class RepaintRunnable implements Runnable {
		@Override
		public void run() {
			repaint();
		}
	}

	public FunctionPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

		final Dimension size = new Dimension(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT_PANE);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);

		points = new LinkedList<PointDouble>();
		userInputPoints = new LinkedList<PointDouble>();

		coordinateSystem = new CoordinateSystemView();

		repaintRunnable = new RepaintRunnable();
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
	public synchronized void paint(Graphics graphics) {
		super.paint(graphics);

		final int[] xPoints = new int[points.size()];
		final int[] yPoints = new int[points.size()];

		graphics.setColor(Color.RED);
		for (int i = 0; i < points.size(); ++i) {
			final PointDouble point = points.get(i);
			xPoints[i] = (int) Math.round(point.x);
			yPoints[i] = (int) Math.round(point.y);
		}

		graphics.drawPolyline(xPoints, yPoints, points.size());

		for (final PointDouble point : userInputPoints) {
			graphics.setColor(Color.BLACK);
			graphics.drawOval((int) point.x - WIDTH_POINT / 2, (int) point.y
					- HEIGHT_POINT / 2, WIDTH_POINT, HEIGHT_POINT);
			graphics.setColor(Color.BLUE);
			graphics.fillOval((int) point.x - WIDTH_POINT / 2, (int) point.y
					- HEIGHT_POINT / 2, WIDTH_POINT, HEIGHT_POINT);
		}

		coordinateSystem.draw(graphics);

		if (isTraining) {
			final int fontStyle = Font.CENTER_BASELINE | Font.BOLD;
			final Font font = new Font(Font.SANS_SERIF, fontStyle,
					TEXT_WAIT_SIZE);
			graphics.setFont(font);

			FontMetrics fm = graphics.getFontMetrics(font);
			Rectangle2D rect = fm.getStringBounds(TEXT_WAIT, graphics);

			int textHeight = (int) (rect.getHeight());
			int textWidth = (int) (rect.getWidth());

			graphics.drawString(TEXT_WAIT, (ScreenInfo.WIDTH - textWidth) / 2,
					(ScreenInfo.HEIGHT_PANE - textHeight) / 2);
		}

	}

	@Override
	public void onUserPointAdded(final List<PointDouble> points) {
		userInputPoints.clear();
		userInputPoints.addAll(points);
		repaint();
	}

	@Override
	public void onApproximationReady(final List<PointDouble> function,
			final double error) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				points.clear();
				points.addAll(function);
				repaint();
			}
		});
	}

	public boolean clear() {
		boolean result;
		if (points.isEmpty()) {
			userInputPoints.clear();
			result = true;
		} else {
			points.clear();
			result = false;
		}
		repaint();
		return result;
	}

	@Override
	public void onTrainStart() {
		isTraining = true;
		SwingUtilities.invokeLater(repaintRunnable);
	}

	@Override
	public void onTrainEnd(final double trainError) {
		isTraining = false;
		SwingUtilities.invokeLater(repaintRunnable);
	}

}
