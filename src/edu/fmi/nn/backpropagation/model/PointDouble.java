package edu.fmi.nn.backpropagation.model;

/**
 * A class that represents a point with real(double) coordinates
 * 
 * @author martin
 * 
 */
public class PointDouble {

	/**
	 * The X-axis value associated with the point
	 */
	public final double x;

	/**
	 * The Y-axis value associated with the point
	 */
	public final double y;

	/**
	 * Creates a new point with the given coordinates
	 * 
	 * @param x
	 *            the x value, associated with the point
	 * @param y
	 *            the y value, associated with the point
	 */
	public PointDouble(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointDouble other = (PointDouble) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

}
