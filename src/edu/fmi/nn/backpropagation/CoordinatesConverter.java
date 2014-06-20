package edu.fmi.nn.backpropagation;

import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;

public class CoordinatesConverter {

	public static PointDouble toScreenCoordinates(final PointDouble point) {
		final double x = point.x * ScreenInfo.WIDTH;
		final double y = point.y * ScreenInfo.HEIGHT_PANE;
		return new PointDouble(x, y);
	}

	public static PointDouble toNetworkCoordinates(final PointDouble point) {
		final double x = point.x / ScreenInfo.WIDTH;
		final double y = point.y / ScreenInfo.HEIGHT_PANE;
		return new PointDouble(x, y);
	}
}
