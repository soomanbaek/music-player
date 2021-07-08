package process;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import action.Get;
import action.PlaySong;
import action.Set;
import design.VolumeBar;

@SuppressWarnings("serial")
public class FunctionDock extends JPanel {
	private JButton loadBtn;
	private JButton repeatBtn;
	public static VolumeBar volume_bar; // Volume Slider which position will be
	// sound level
	PlaySong playAction = new PlaySong();

	final URL icon_repeat = getClass().getResource(
			"/images/icons/repeat.png");
	final URL icon_repeat_clicked = getClass().getResource(
		"/images/icons/repeat_clicked.png");
	
	public FunctionDock() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		makePanel();
		setOpaque(false); // transparent option : false is actually true
	}

	private void makePanel() {
		/* Icon Load */
		final URL icon_load = getClass().getResource("/images/icons/load.png");


		/* Make Button with Icon */
		loadBtn = new JButton(new ImageIcon(icon_load));
		loadBtn.setBorder(null);
		loadBtn.setFocusable(false);
		loadBtn.setContentAreaFilled(false);
		loadBtn.addActionListener(new LoadBtnAction());
		loadBtn.setMaximumSize(new Dimension(20, 20));

		
		repeatBtn = new JButton(new ImageIcon(icon_repeat));
		repeatBtn.setBorder(null);
		repeatBtn.setFocusable(false);
		repeatBtn.setContentAreaFilled(false);
		repeatBtn.setMaximumSize(new Dimension(20, 20));
		repeatBtn.addActionListener(new repeatBtnAction());
		
		
		/* Volume Bar */
		volume_bar = new VolumeBar();
		volume_bar.setMaximumSize(new Dimension(50,10));
		
		this.add(volume_bar);
		this.add(repeatBtn);
		this.add(loadBtn);
	}
	
	class LoadBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Get get = new Get();
			get.song();
			Set set = new Set();
			set.song(get);
			MusicPlayer.list_panel.refreshList();
		}
	}	class repeatBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MusicPlayer.repeat = !MusicPlayer.repeat;
			if (MusicPlayer.repeat) {
				repeatBtn.setIcon(new ImageIcon(icon_repeat_clicked));
			} else {
				repeatBtn.setIcon(new ImageIcon(icon_repeat));

			}
		}
	}
}