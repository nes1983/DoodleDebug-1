package view;

import javax.swing.JFrame;

/**
 * First test frame for visualization
 * @author Cedric Reichenbach
 *
 */
public class DoodleFrame extends JFrame {

	public DoodleFrame(DoodleCanvas canvas) {
		super("first testframe");
		this.initialize();
		this.setContentPane(new SwingAdapter(canvas));
	}

	private void initialize() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocation(160, 100);
		this.setSize(800, 500);
	}

}