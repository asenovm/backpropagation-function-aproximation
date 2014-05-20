package edu.fmi.nn.backpropagation;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FunctionApproximation {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final JFrame frame = new JFrame("Function Approximation");
				
				final Dimension size = new Dimension(800, 600);
				frame.setMinimumSize(size);
				frame.setMaximumSize(size);
				frame.setPreferredSize(size);
				
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
