package edu.fmi.nn.backpropagation.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import edu.fmi.nn.backpropagation.ModelListener;
import edu.fmi.nn.backpropagation.model.FunctionModel;

public class FunctionView extends JFrame implements ModelListener {

	private FunctionPanel functionPanel;

	public FunctionView(String title, GraphicsConfiguration gc) {
		super(title, gc);

		functionPanel = new FunctionPanel();
		add(functionPanel);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

}
