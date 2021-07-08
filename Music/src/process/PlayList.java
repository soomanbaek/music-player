package process;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PlayList extends JFrame {

	public PlayList() {
		setSize(200, 300);
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		Dimension windowSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Make screen position next to main
		this.setLocation(screenSize.width / 2 + windowSize.width,
				screenSize.height / 2 - windowSize.height / 2);
	}

	public void showList() {
		super.setVisible(true);
	}
}