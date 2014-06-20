package edu.fmi.nn.backpropagation;

import static edu.fmi.nn.backpropagation.view.FunctionPanel.HEIGHT_FRAME;
import static edu.fmi.nn.backpropagation.view.FunctionPanel.WIDTH_FRAME;
import edu.fmi.nn.backpropagation.model.PointDouble;

public class CoordinatesConverter {

	public static PointDouble toScreenCoordinates(final PointDouble point) {
		final double x = point.x * WIDTH_FRAME;
		final double y = point.y * HEIGHT_FRAME;
		return new PointDouble(x, y);
	}

	public static PointDouble toNetworkCoordinates(final PointDouble point) {
		final double x = point.x / WIDTH_FRAME;
		final double y = point.y / HEIGHT_FRAME;
		return new PointDouble(x, y);
	}
}
