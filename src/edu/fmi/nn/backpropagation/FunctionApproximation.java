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

			for (int i = 0; i < 2500; ++i) {
				for (final PointDouble point : trainPoints) {
					network.train(point);
				}
				view.onApproximationReady(getApproximation());	
			}
			view.onTrainEnd(0);
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
