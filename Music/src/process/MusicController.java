package process;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import javazoom.jl.decoder.JavaLayerException;
import action.Get;
import action.PlaySong;
import action.Set;

@SuppressWarnings("serial")
public class MusicController extends JPanel {

	private JButton previousBtn;
	private JButton nextBtn;
	private JButton onoffBtn;
	private JButton stopBtn;
	final URL icon_previous = getClass().getResource("/images/icons/previous.png");
	final URL icon_next = getClass().getResource("/images/icons/next.png");
	final URL icon_play = getClass().getResource("/images/icons/play.png");
	final URL icon_pause = getClass().getResource("/images/icons/pause.png");
	final URL icon_stop = getClass().getResource("/images/icons/stop.png");
	PlaySong playAction = new PlaySong();

	public MusicController() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(168, 70);
		buildPanel();
		switch_btn(false);
		setOpaque(false);
	}
 
	private void makeButtons() {
		previousBtn = new JButton(new ImageIcon(icon_previous));
		previousBtn.setBorder(null);
		previousBtn.setFocusable(false);
		previousBtn.setContentAreaFilled(false);
		previousBtn.setMaximumSize(new Dimension(25, 25));
		previousBtn.addActionListener(new previousBtnAction());

		nextBtn = new JButton(new ImageIcon(icon_next));
		nextBtn.setBorder(null);
		nextBtn.setFocusable(false);
		nextBtn.setContentAreaFilled(false);
		nextBtn.setMaximumSize(new Dimension(25, 25));
		nextBtn.addActionListener(new nextBtnAction());

		onoffBtn = new JButton(new ImageIcon(icon_play));
		onoffBtn.setBorder(null);
		onoffBtn.setFocusable(false);
		onoffBtn.setContentAreaFilled(false);
		onoffBtn.setMaximumSize(new Dimension(50, 50));
		onoffBtn.addActionListener(new onoffBtnAction());
		
		stopBtn = new JButton(new ImageIcon(icon_stop));
		stopBtn.setBorder(null);
		stopBtn.setFocusable(false);
		stopBtn.setContentAreaFilled(false);
		stopBtn.setMaximumSize(new Dimension(50, 50));
		stopBtn.addActionListener(new stopBtnAction());
	}

	private void buildPanel() {
		makeButtons();
		this.add(Box.createRigidArea(new Dimension(18, 0))); /* empty block */
		this.add(previousBtn);
		this.add(Box.createRigidArea(new Dimension(10, 0))); /* empty block */
		this.add(stopBtn);
		this.add(Box.createRigidArea(new Dimension(10, 0))); /* empty block */
		this.add(onoffBtn);
		this.add(Box.createRigidArea(new Dimension(10, 0))); /* empty block */
		this.add(nextBtn);
		this.add(Box.createRigidArea(new Dimension(18, 0))); /* empty block */
	}

	public void switch_btn(boolean playing) {
		if (playing) {
			onoffBtn.setIcon(new ImageIcon(icon_pause));
		} else {
			onoffBtn.setIcon(new ImageIcon(icon_play));
		}
	}

	class previousBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MusicPlayer.repeat=false;
			playAction.playPrevious();
		}
	}

	class nextBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MusicPlayer.repeat=false;
			playAction.playNext();
		}
	}

	class onoffBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (MusicPlayer.list.size() == 0) {
				Get get = new Get();
				get.song();
				Set set = new Set();
				set.song(get);
				playAction.readySong();
				playAction.playSong();
			}
			if (MusicPlayer.play_mp3 != null) {
				
				// NOTSTARTED = 0;
				// PLAYING = 1;
				// PAUSED = 2;
				// FINISHED = 3; auto play mod -> must not showing

				try {
					switch (MusicPlayer.play_mp3.getStatus()) {
					case 0:
						MusicPlayer.play_mp3.play();
						break;
					case 1:
						MusicPlayer.ctr_panel.switch_btn(false);
						MusicPlayer.play_mp3.pause();
						break;
					case 2:
						MusicPlayer.play_mp3.play();
						break;
					case 3:
						MusicPlayer.play_mp3.play();
						break;
					}
				} catch (JavaLayerException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	class stopBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MusicPlayer.play_mp3.stop();
			playAction.readySong();
		}
	}
	
}