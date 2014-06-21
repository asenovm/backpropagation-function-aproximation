package edu.fmi.nn.backpropagation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.model.FunctionModel;
import edu.fmi.nn.backpropagation.model.NetworkConfiguration;
import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;
import edu.fmi.nn.backpropagation.view.FunctionView;
import edu.fmi.nn.backpropagation.view.ViewCallback;

public class FunctionApproximation implements ViewCallback {

	private final FunctionModel model;

	private final FunctionView view;

	private NeuralNetwork network;

	private final ExecutorService executor;

	private final ApproximationRunnable approximationRunnable;

	private class ApproximationRunnable implements Runnable {

		private NetworkConfiguration configuration;

		@Override
		public void run() {
			view.onTrainStart();

			final List<PointDouble> points = model.getPoints();
			final List<PointDouble> trainPoints = new LinkedList<PointDouble>();
			for (final PointDouble point : points) {
				trainPoints.add(CoordinatesConverter
						.toNetworkCoordinates(point));
			}

			double error = 0.0;
			for (int i = 0; i < configuration.getTrainCount(); ++i) {
				error = 0.0;
				for (final PointDouble point : trainPoints) {
					network.train(point);
				}

				for (final PointDouble point : trainPoints) {
					error += network.getErrorForPoint(point);
				}

				if (error < configuration.getTolerance()) {
					break;
				}

				view.onApproximationReady(getApproximation(), error);
			}
			view.onTrainEnd(error);
		}
	}

	public FunctionApproximation() {
		final NetworkConfiguration configuration = new NetworkConfiguration();

		network = new NeuralNetwork(configuration);

		model = new FunctionModel();
		view = new FunctionView();

		view.addMouseListener(model);
		view.setCallback(this);
		model.setListener(view);

		executor = Executors.newSingleThreadExecutor();
		approximationRunnable = new ApproximationRunnable();
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
	public void onApproximateClicked(final NetworkConfiguration configuration) {
		approximationRunnable.configuration = configuration;
		executor.execute(approximationRunnable);
	}

	@Override
	public void onClearClicked(final NetworkConfiguration configuration) {
		model.clear();
		network = new NeuralNetwork(configuration);
	}

	private List<PointDouble> getApproximation() {
		final List<PointDouble> approximation = new LinkedList<PointDouble>();
		for (int i = 0; i < ScreenInfo.WIDTH; ++i) {
			final double[] input = new double[] { (double) i / ScreenInfo.WIDTH };
			final double[] output = network.computeOutputs(input);
			approximation.add(CoordinatesConverter
					.toScreenCoordinates(new PointDouble(input[0], output[0])));
		}

		return approximation;
	}
}
