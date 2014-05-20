package edu.fmi.nn.backpropagation.view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class FunctionFrame extends JFrame {

	/**
	 * {@value}
	 */
	private static final int WIDTH_FRAME = 800;
	
	/**
	 * {@value}
	 */
	private static final int HEIGHT_FRAME = 600;
	
	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -4178865610705123099L;

	public FunctionFrame() throws HeadlessException {
		this("", null);
	}

	public FunctionFrame(GraphicsConfiguration gc) {
		this("", gc);
	}

	public FunctionFrame(String title) throws HeadlessException {
		this(title, null);
	}

	public FunctionFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		
		final Dimension size = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		
		pack();
		setVisible(true);
	}

}
