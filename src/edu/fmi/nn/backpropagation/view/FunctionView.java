package edu.fmi.nn.backpropagation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;

import edu.fmi.nn.backpropagation.ComputationCallback;
import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.NetworkConfiguration;
import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;

public class FunctionView extends JFrame implements ModelListener,
		ViewCallback, ComputationCallback {

	/**
	 * {@value}
	 */
	private static final String TITLE_APP = "Function Approximation";

	private final MenuPanel menuPanel;

	private final FunctionPanel functionPanel;

	private ViewCallback callback;

	public FunctionView(String title, GraphicsConfiguration gc) {
		super(TITLE_APP, gc);
		setLayout(new BorderLayout());

		final Dimension dimension = new Dimension(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT_SCREEN);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		functionPanel = new FunctionPanel();
		add(functionPanel, BorderLayout.PAGE_START);

		menuPanel = new MenuPanel();
		menuPanel.setCallback(this);
		add(menuPanel, BorderLayout.PAGE_END);

		pack();
		setVisible(true);
	}

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

	public void setCallback(final ViewCallback callback) {
		this.callback = callback;
	}

	@Override
	public void onUserPointAdded(final List<PointDouble> points) {
		functionPanel.onUserPointAdded(points);
	}

	@Override
	public void onApproximationReady(List<PointDouble> function) {
		functionPanel.onApproximationReady(function);
	}

	@Override
	public void onClearClicked() {
		if (functionPanel.clear()) {
			callback.onClearClicked();
		}
	}

	@Override
	public void onApproximateClicked(final NetworkConfiguration configuration) {
		callback.onApproximateClicked(configuration);
	}

	@Override
	public void onTrainStart() {
		menuPanel.onStartTraining();
		functionPanel.onStartTraining();
	}

	@Override
	public void onTrainEnd() {
		menuPanel.onEndTraining();
		functionPanel.onEndTraining();
	}

}
