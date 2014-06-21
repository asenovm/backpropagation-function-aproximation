package edu.fmi.nn.backpropagation;

import edu.fmi.nn.backpropagation.model.PointDouble;
import edu.fmi.nn.backpropagation.model.ScreenInfo;

/**
 * A utility class to aid the conversion of the screen points to neural-network
 * specific coordinates
 * 
 * @author martin
 * 
 */
public class CoordinatesConverter {

	/**
	 * Returns the representation in screen coordinates of the given input
	 * <tt>point</tt>
	 * 
	 * @param point
	 *            the point which is to be converted from neural network
	 *            specific to screen coordinates
	 * 
	 * @return the representation in screen coordinates of the given input
	 *         <tt>point</tt>
	 */
	public static PointDouble toScreenCoordinates(final PointDouble point) {
		final double x = point.x * ScreenInfo.WIDTH;
		final double y = point.y * ScreenInfo.HEIGHT_PANE;
		return new PointDouble(x, y);
	}

	/**
	 * 
	 * Returns the representation in network-specific coordinates of the given
	 * input <tt>point</tt>
	 * 
	 * @param point
	 *            the point that is to be converted from screen to
	 *            network-specific coordinates
	 * 
	 * @return the representation in network-specific coordinates of the given
	 *         input <tt>point</tt>
	 */
	public static PointDouble toNetworkCoordinates(final PointDouble point) {
		final double x = point.x / ScreenInfo.WIDTH;
		final double y = point.y / ScreenInfo.HEIGHT_PANE;
		return new PointDouble(x, y);
	}
}
