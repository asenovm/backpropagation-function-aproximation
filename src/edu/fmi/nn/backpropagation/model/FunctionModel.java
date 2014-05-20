package edu.fmi.nn.backpropagation.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class FunctionModel {

	private final List<Point> points;

	public FunctionModel() {
		points = new LinkedList<Point>();
	}

	public void add(final int x, final int y) {
		points.add(new Point(x, y));
	}
}
