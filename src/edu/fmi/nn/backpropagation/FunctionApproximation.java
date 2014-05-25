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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// SwingUtilities.invokeLater(new Runnable() {
		//
		// @Override
		// public void run() {
		// final FunctionApproximation functionApproximation = new
		// FunctionApproximation();
		// }
		// });
		NeuralNetwork network = new NeuralNetwork(3, 4, 2);

		double[] inputValues = new double[] { 1.0, 2.0, 3.0 };
		double[] initialOutputs = network.computeOutputs(inputValues);
		double[] targetValues = new double[] { -0.8500, 0.7500 };

//		double learningRate = 0.90;
//		double momentum = 0.04;
//
//		int epochsCount = 0;
//		double[] yValues = network.computeOutputs(inputValues);

//		double error = error(targetValues, yValues);
//
//		while (epochsCount < 1000 && error > 0.01) {
//			network.updateWeights(targetValues, learningRate, momentum);
//			yValues = network.computeOutputs(inputValues);
//			error = error(targetValues, yValues);
//			++epochsCount;
//		}
//		double[] bestWeights = network.getWeights();
//		Helpers.ShowVector(bestWeights, 3, true);
	}

	public static double error(double[] target, double[] output) {
		double sum = 0.0;
		for (int i = 0; i < target.length; ++i)
			sum += Math.abs(target[i] - output[i]);
		return sum;
	}
}
