package edu.fmi.nn.backpropagation;

import javax.swing.SwingUtilities;

import edu.fmi.nn.backpropagation.view.FunctionFrame;

public class FunctionApproximation {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FunctionFrame("Function Approximation");
			}
		});
	}
}
