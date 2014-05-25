package edu.fmi.nn.backpropagation;

import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.model.FunctionModel;
import edu.fmi.nn.backpropagation.view.FunctionView;

public class FunctionApproximation {

	private final FunctionModel model;

	private final FunctionView view;

	public FunctionApproximation() {
		model = new FunctionModel();
		view = new FunctionView("Function Approximation");

		view.addMouseListener(model);
		model.setListener(view);
	}

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
}
