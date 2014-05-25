package edu.fmi.nn.backpropagation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	/**
	 * {@value}
	 */
	private static final int HEIGHT_BUTTON = 30;

	/**
	 * {@value}
	 */
	private static final int WIDTH_BUTTON = 100;

	/**
	 * {@value}
	 */
	private static final String TEXT_RESET = "Reset";

	/**
	 * {@value}
	 */
	private static final String TEXT_TRAIN = "Train";

	/**
	 * {@value}
	 */
	private static final int HEIGHT_PANEL_MENU = 200;

	/**
	 * {@value}
	 */
	private static final int WIDTH_PANEL_MENU = 800;

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -5663344373041037735L;

	private ViewCallback callback;

	private class TrainOnMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			callback.onTrainClicked();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// blank
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// blank
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// blank
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// blank
		}

	}

	private class ResetOnMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			callback.onResetClicked();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// blank
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// blank
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// blank
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// blank
		}

	}

	public MenuPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

		final Dimension dimension = new Dimension(WIDTH_PANEL_MENU,
				HEIGHT_PANEL_MENU);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);

		createAndAddButton(TEXT_TRAIN, new TrainOnMouseListener());
		createAndAddButton(TEXT_RESET, new ResetOnMouseListener());

		setBackground(Color.GRAY);
	}

	public MenuPanel() {
		this(false);
	}

	public MenuPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(FlowLayout.LEFT), isDoubleBuffered);
	}

	public MenuPanel(LayoutManager layout) {
		this(layout, false);
	}

	public void setCallback(final ViewCallback callback) {
		this.callback = callback;
	}

	private void createAndAddButton(final String text,
			final MouseListener listener) {
		final Dimension dimension = new Dimension(WIDTH_BUTTON, HEIGHT_BUTTON);

		final JButton button = new JButton(text);
		button.setMinimumSize(dimension);
		button.setPreferredSize(dimension);
		button.setFocusPainted(false);
		button.addMouseListener(listener);
		add(button);
	}

}
