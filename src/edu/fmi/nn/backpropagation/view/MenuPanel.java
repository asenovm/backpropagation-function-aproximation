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

import edu.fmi.nn.backpropagation.ComputationCallback;
import edu.fmi.nn.backpropagation.model.NetworkConfiguration;

public class MenuPanel extends JPanel implements ComputationCallback {

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

	private final JTextArea hiddenUnits;

	private final JTextArea learningRate;

	private final JTextArea momentum;

	private final JTextArea epochs;

	private class ApproximateOnMouseListener extends SimpleOnMouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			final int hiddenUnitsCount = Integer
					.parseInt(hiddenUnits.getText());
			final double learningRateValue = Double.parseDouble(learningRate
					.getText());
			final double momentumValue = Double.parseDouble(momentum.getText());
			final int epochsValue = Integer.parseInt(epochs.getText());
			final NetworkConfiguration configuration = new NetworkConfiguration(
					epochsValue, hiddenUnitsCount, learningRateValue,
					momentumValue);
			callback.onApproximateClicked(configuration);
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

		hiddenUnits = createAndAddConfigurationField("Hidden Units Count:",
				"6", 40, 20);
		learningRate = createAndAddConfigurationField("Learning Rate:", "0.01",
				40, 20);
		momentum = createAndAddConfigurationField("Momentum", "0.1", 40, 20);
		epochs = createAndAddConfigurationField("Number of epochs:", "1000000",
				80, 20);

		approximateButton = createAndAddButton(TEXT_APPROXIMATE,
				new ApproximateOnMouseListener());
		clearButton = createAndAddButton(TEXT_CLEAR, new ClearOnMouseListener());
	}

	public JTextArea createAndAddConfigurationField(final String labelText,
			final String defaultValue, final int width, final int height) {
		final JLabel label = new JLabel(labelText);
		final JTextArea textArea = new JTextArea(defaultValue);
		final Dimension textAreaDimension = new Dimension(width, height);

		textArea.setPreferredSize(textAreaDimension);
		textArea.setMinimumSize(textAreaDimension);
		textArea.setMaximumSize(textAreaDimension);

		add(label);
		add(textArea);

		return textArea;
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

	public void onTrainStart() {
		approximateButton.setEnabled(false);
		clearButton.setEnabled(false);
	}

	public void onTrainEnd() {
		approximateButton.setEnabled(true);
		clearButton.setEnabled(true);
	}

}
