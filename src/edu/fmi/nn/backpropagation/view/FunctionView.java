package edu.fmi.nn.backpropagation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.FunctionModel;

public class FunctionView extends JFrame implements ModelListener, ViewCallback {

	/**
	 * {@value}
	 */
	private static final int HEIGHT_FRAME = 800;

	/**
	 * {@value}
	 */
	private static final int WIDTH_FRAME = 800;

	private FunctionPanel functionPanel;

	private ViewCallback callback;

	public FunctionView(String title, GraphicsConfiguration gc) {
		super(title, gc);
		setLayout(new BorderLayout());

		final Dimension dimension = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		functionPanel = new FunctionPanel();
		add(functionPanel, BorderLayout.PAGE_START);

		final MenuPanel panel = new MenuPanel();
		panel.setCallback(this);
		add(panel, BorderLayout.PAGE_END);

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

	@Override
	public void onModelChanged(FunctionModel model) {
		functionPanel.onModelChanged(model);
	}

	public void setCallback(final ViewCallback callback) {
		this.callback = callback;
	}

	@Override
	public void onTrainClicked() {
		callback.onTrainClicked();
	}

	@Override
	public void onResetClicked() {
		callback.onResetClicked();
	}

}
