package edu.fmi.nn.backpropagation;

import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.model.FunctionModel;
import edu.fmi.nn.backpropagation.view.FunctionView;
import edu.fmi.nn.backpropagation.view.ViewCallback;

public class FunctionApproximation implements ViewCallback {

	/**
	 * {@value}
	 */
	private static final String TITLE_APP = "Function Approximation";

	private final FunctionModel model;

	private final FunctionView view;

	public FunctionApproximation() {
		model = new FunctionModel();
		view = new FunctionView(TITLE_APP);

		view.addMouseListener(model);
		view.setCallback(this);
		model.setListener(view);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final FunctionApproximation approximation = new FunctionApproximation();
			}
		});
		NeuralNetwork network = new NeuralNetwork(3, 4, 2);
		network.train();
	}

	@Override
	public void onTrainClicked() {
		System.out.println("train clicked!");
	}

	@Override
	public void onResetClicked() {
		System.out.println("reset clicked");
	}
}
