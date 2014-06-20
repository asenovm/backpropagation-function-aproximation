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
	private static final int WIDTH_BUTTON = 130;

	/**
	 * {@value}
	 */
	private static final String TEXT_CLEAR = "Clear";

	/**
	 * {@value}
	 */
	private static final String TEXT_APPROXIMATE = "Approximate";

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

	private final JButton approximateButton;

	private final JButton clearButton;

	private class ApproximateOnMouseListener extends SimpleOnMouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			callback.onApproximateClicked();
		}
	}

	private class ClearOnMouseListener extends SimpleOnMouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			callback.onClearClicked();
		}
	}

	public MenuPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

		final Dimension dimension = new Dimension(WIDTH_PANEL_MENU,
				HEIGHT_PANEL_MENU);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);

		approximateButton = createAndAddButton(TEXT_APPROXIMATE,
				new ApproximateOnMouseListener());
		clearButton = createAndAddButton(TEXT_CLEAR, new ClearOnMouseListener());

		setBackground(Color.GRAY);
	}

	public MenuPanel() {
		this(false);
	}

	public MenuPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(FlowLayout.CENTER), isDoubleBuffered);
	}

	public MenuPanel(LayoutManager layout) {
		this(layout, false);
	}

	public void setCallback(final ViewCallback callback) {
		this.callback = callback;
	}

	private JButton createAndAddButton(final String text,
			final MouseListener listener) {
		final Dimension dimension = new Dimension(WIDTH_BUTTON, HEIGHT_BUTTON);

		final JButton button = new JButton(text);
		button.setMinimumSize(dimension);
		button.setPreferredSize(dimension);
		button.setFocusPainted(false);
		button.addMouseListener(listener);
		add(button);

		return button;
	}

	public void onStartTraining() {
		approximateButton.setEnabled(false);
		clearButton.setEnabled(false);
	}

	public void onEndTraining() {
		approximateButton.setEnabled(true);
		clearButton.setEnabled(true);
	}

}
