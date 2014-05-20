package edu.fmi.nn.backpropagation.view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class FunctionView extends JFrame {

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

	private class FunctionMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// blank
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// blank
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}

	public FunctionView() throws HeadlessException {
		this("", null);
	}

	public FunctionView(GraphicsConfiguration gc) {
		this("", gc);
	}

	public FunctionView(String title) throws HeadlessException {
		this(title, null);
	}

	public FunctionView(String title, GraphicsConfiguration gc) {
		super(title, gc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Dimension size = new Dimension(WIDTH_FRAME, HEIGHT_FRAME);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);

		addMouseListener(new FunctionMouseListener());

		pack();
		setVisible(true);
	}

}
