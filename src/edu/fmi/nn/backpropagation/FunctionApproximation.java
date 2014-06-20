package edu.fmi.nn.backpropagation;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.model.FunctionModel;
import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;
import edu.fmi.nn.backpropagation.view.FunctionView;
import edu.fmi.nn.backpropagation.view.ViewCallback;

public class FunctionApproximation implements ViewCallback {

	/**
	 * {@value}
	 */
	private static final String TITLE_APP = "Function Approximation";

	private final FunctionModel model;

	private final FunctionView view;

	private final NeuralNetwork network;

	public FunctionApproximation() {
		network = new NeuralNetwork();

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
	}

	@Override
	public void onTrainClicked() {
		final List<PointDouble> points = model.getPoints();
		final List<PointDouble> trainPoints = new LinkedList<PointDouble>();
		for (final PointDouble point : points) {
			trainPoints.add(CoordinatesConverter.toNetworkCoordinates(point));
		}
		network.train(trainPoints);

	}

	private List<PointDouble> getApproximation(final List<PointDouble> points,
			final NeuralNetwork network) {
		final List<PointDouble> approximation = new LinkedList<PointDouble>();
		int j = 0;
		for (int i = 0; i < ScreenInfo.WIDTH / points.size(); ++i) {
			final double[] input = new double[points.size()];
			for (int p = 0; p < points.size(); ++p) {
				input[p] = j++ / (double) ScreenInfo.WIDTH;
			}
			final double[] output = network.computeOutputs(input);
			for (int t = 0; t < points.size(); ++t) {
				approximation.add(CoordinatesConverter
						.toScreenCoordinates(new PointDouble(input[t],
								output[t])));
			}
		}
		return approximation;
	}

	@Override
	public void onClearClicked() {
		// blank as for now
	}

	@Override
	public void onApproximateClicked() {
		view.onApproximationReady(getApproximation(model.getPoints(), network));
	}
}
