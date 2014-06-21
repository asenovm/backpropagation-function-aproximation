package edu.fmi.nn.backpropagation;

import java.util.Arrays;
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

	private final NeuralNetwork network;

	private final ExecutorService executor;

	private final ApproximationRunnable approximationRunnable;

	private class ApproximationRunnable implements Runnable {

		public NetworkConfiguration configuration;

		@Override
		public void run() {
			view.onTrainStart();

			final List<PointDouble> points = model.getPoints();
			final List<PointDouble> trainPoints = new LinkedList<PointDouble>();
			for (final PointDouble point : points) {
				trainPoints.add(CoordinatesConverter
						.toNetworkCoordinates(point));
			}
			network.setConfiguration(configuration);

			final double error = network.train(trainPoints);

			view.onApproximationReady(getApproximation());
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
	public void onClearClicked() {
		model.clear();
	}

	private List<PointDouble> getApproximation() {
		final List<PointDouble> points = model.getPoints();
		final List<PointDouble> approximation = new LinkedList<PointDouble>();

		int j = 0;
		for (int i = 0; i < ScreenInfo.WIDTH / points.size(); ++i) {
			final double[] input = new double[points.size()];

			for (int p = 0; p < points.size(); ++p) {
				input[p] = j++ / (double) ScreenInfo.WIDTH;
			}

			final double[] output = network.computeOutputs(input);

			for (int t = 0; t < points.size(); ++t) {
				final PointDouble outputPoint = new PointDouble(input[t],
						output[t]);
				approximation.add(CoordinatesConverter
						.toScreenCoordinates(outputPoint));
			}
		}
		return approximation;
	}

}
