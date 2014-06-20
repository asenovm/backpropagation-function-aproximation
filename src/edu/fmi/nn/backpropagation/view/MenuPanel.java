package edu.fmi.nn.backpropagation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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

		setBackground(Color.GRAY);

		createAndAddConfigurationField("Hidden Units Count:", "6", 40, 20);
		createAndAddConfigurationField("Learning Rate:", "0.01", 40, 20);
		createAndAddConfigurationField("Momentum", "0.1", 40, 20);
		createAndAddConfigurationField("Number of epochs:", "1000000", 80, 20);

		approximateButton = createAndAddButton(TEXT_APPROXIMATE,
				new ApproximateOnMouseListener());
		clearButton = createAndAddButton(TEXT_CLEAR, new ClearOnMouseListener());
	}

	public void createAndAddConfigurationField(final String label,
			final String defaultValue, final int width, final int height) {
		final JLabel epochs = new JLabel(label);
		final JTextArea epochsArea = new JTextArea(defaultValue);
		final Dimension epochsFieldDimension = new Dimension(width, height);
		epochsArea.setPreferredSize(epochsFieldDimension);
		epochsArea.setMinimumSize(epochsFieldDimension);
		epochsArea.setMaximumSize(epochsFieldDimension);
		add(epochs);
		add(epochsArea);

	}

	public MenuPanel() {
		this(false);
	}

	public MenuPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(FlowLayout.CENTER, 10, 20), isDoubleBuffered);
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
