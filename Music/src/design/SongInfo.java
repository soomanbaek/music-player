package design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import process.MusicPlayer;
import action.Get;
import action.Set;

@SuppressWarnings("serial")
public class SongInfo extends JPanel {
	private JLabel singerInfo;
	private JLabel songNameInfo;
	private JLabel fileNameInfo;
	public String singerName;
	public String songName;
	public String fileName;
	private boolean showFileName = false;

	public SongInfo() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		setSize(223, 60);
		getSongInfo("", "");
		loadInfoPanel();
	}

	public void getSongInfo(String songName, String singerName) {
		if (songName != null & singerName != null) {
			this.singerName = singerName;
			this.songName = songName;
			showFileName = false;
		} else {
			this.fileName = new File(MusicPlayer.list.get(MusicPlayer.queue))
					.getName();
			showFileName = true;
		}
	}

	private void makeLabel() {
		// Foreground = text color
		if (showFileName) {
			fileNameInfo = new JLabel(fileName);
			fileNameInfo.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
			fileNameInfo.setForeground(Color.BLACK); // text color
			fileNameInfo.setAlignmentX(CENTER_ALIGNMENT);
		} else {
			singerInfo = new JLabel(singerName);
			singerInfo.setFont(new Font("Malgun Gothic", Font.PLAIN, 10));
			singerInfo.setForeground(Color.GRAY);
			singerInfo.setAlignmentX(CENTER_ALIGNMENT);
			singerInfo.addMouseListener(new EncodeAction());

			songNameInfo = new JLabel(songName);
			songNameInfo.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
			songNameInfo.setForeground(Color.BLACK);
			songNameInfo.setAlignmentX(CENTER_ALIGNMENT);
			songNameInfo.addMouseListener(new EncodeAction());

		}
	}

	public void loadInfoPanel() {
		makeLabel();
		if (showFileName) {
			this.add(Box.createRigidArea(new Dimension(19, 0))); /* empty block */
			this.add(fileNameInfo);
			this.add(Box.createRigidArea(new Dimension(19, 0))); /* empty block */
		} else {
			this.add(songNameInfo);
			this.add(Box.createRigidArea(new Dimension(3, 0))); /* empty block */
			this.add(singerInfo);
			this.add(Box.createRigidArea(new Dimension(15, 0))); /* empty block */
			// this.add(timer);
		}
	}

	class EncodeAction implements MouseListener {
		String object;

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				Get get = new Get();
				get.metaData(false);
				Set set = new Set();
				set.fixEncode(get.songName(), get.singerName());
			}
		}

		public void mouseEntered(MouseEvent e1) {
		}

		public void mouseExited(MouseEvent e2) {
		}

		public void mouseReleased(MouseEvent e3) {
		}

		public void mousePressed(MouseEvent e4) {
		}
	}

	public String songName() {
		return songName;
	}

	public String singerName() {
		return singerName;
	}
}