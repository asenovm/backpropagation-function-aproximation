package edu.fmi.nn.backpropagation;

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
		NeuralNetwork network = new NeuralNetwork(3, 4, 2);
		network.train();
	}
}
