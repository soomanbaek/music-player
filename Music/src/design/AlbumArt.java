package design;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AlbumArt extends JLabel {
	final URL default_img = getClass().getResource("/images/background/pi.png"); // Default Bg
	private ImageIcon defaultBg = new ImageIcon(default_img);
	public AlbumArt() {
		this.setIcon(defaultBg); 
	}
	public void changeBg(Image img) {
		if(img!=null) {
		img=img.getScaledInstance(124, 124, Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(img));
		repaint(); 
		}
		else {
			this.setIcon(defaultBg);
			repaint(); 
		}
			
	}
}