package process;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import design.AlbumArt;
import design.SongInfo;

@SuppressWarnings("serial")
final public class MusicPlayer extends JFrame {
	// Resources
	public static MusicPlayer SMPlayer;
	public static SongInfo info_panel; // Notify SongInfo to user
	public static MusicController ctr_panel; // Music Contoroller -> Previous,
												// Play, Pause, Resume, Next
	public static FunctionDock dock_panel; // Items Dock as Add Item, Two Lists,
											// and so on
	public static ListPanel list_panel;

	public static AlbumArt albumArt; // Get , Set AlbumArt from Music
	public static JFileChooser browser; // File Browser
	public static Mp3Player play_mp3; // Play Instance for mp3 extension
	// public static DemandList demand_list; // When Opened this player , create
	// , Until Exit, saved at ram
	// public static PlayList play_list; // When Opened this player , load data
	// , When Exit, save data
	public static ArrayList<String> list = new ArrayList<String>();
	private JButton minimiseBtn; // remove screen still playing
	private JButton closeBtn; // Exit This Program
	private JButton minimodeBtn;
	public static JScrollPane scrollBar;
	private JPanel dragArea=new JPanel();
	public static int queue = 0; // Songs Order , 0~N ( N < 100 ) limited to 100
									// songs
	public static boolean changed = false; // When GoNext , GoPrevious or Play
											// at demand list , false -> true ,
											// to notify no need to changing
	public static boolean repeat = false;
	public static boolean firstSet = true;
	static Point mouseDownCompCoords; // I don't know , but It tracks mouse

	private JPanel paint_w; // White Square to painting
	private JPanel paint_w2;
	private JPanel paint_w3;
	private JPanel paint_g; // Grey Square to painting
	
	protected URL icon_minimode;
	protected URL icon_expand;
	public MusicPlayer() {
		mouseDownCompCoords = null;
		setLayout(new BorderLayout());
		setSize(350, 400); // ( width, height )
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* Exit_on_close = exit all jframe , Dispose_on_close = exit only this */

		setResizable(false); // Not allow to spoil Design
		setUndecorated(true); // Do not show OS specific frame
		setLocationRelativeTo(null); /* make it locate center */

		// Enabling Mouse Button Tracking
		dragArea.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				mouseDownCompCoords = null;
			}

			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		// Enabling Draging Position Tracking
		dragArea.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
				SMPlayer.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y
						- mouseDownCompCoords.y);
			}
		});
		add(buildContentPane()); /* load Contents */
	}

	private void makeContents() {

		/* Panel */
		info_panel = new SongInfo();
		ctr_panel = new MusicController();
		dock_panel = new FunctionDock();

		/* Lists */
		list_panel = new ListPanel();

		/* Album Art */
		albumArt = new AlbumArt();

		/* Paints */
		paint_w = paint(new Color(255,255,255));
		paint_w2 = paint(new Color(255,255,255));
		paint_w3 = paint(new Color(255,255,255));
		paint_g = paint(new Color(179, 179, 179));

		/* File Browser Setting */
		browser = new JFileChooser();
		FileFilter songFilter = new FileNameExtensionFilter("Music File",
				"mp3", "wav"); // 'showing name' , 'extensions .... '
								// ,,,,
		browser.addChoosableFileFilter(songFilter);
		browser.setFileFilter(songFilter); // make own filter to default
		browser.setMultiSelectionEnabled(true); // but can't use drag ...
		browser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // can
																			// select
																			// file
																			// or
																			// dir

		/* Icons _ minimize / close */
		final URL icon_mini = getClass().getResource("/images/icons/mini.png");
		minimiseBtn = new JButton(new ImageIcon(icon_mini));
		minimiseBtn.addActionListener(new MinimizeAction());

		minimiseBtn.setBorder(null);
		minimiseBtn.setFocusable(false);
		minimiseBtn.setContentAreaFilled(false);

		final URL icon_close = getClass()
				.getResource("/images/icons/close.png");
		closeBtn = new JButton(new ImageIcon(icon_close));
		closeBtn.addActionListener(new CloseAction());

		closeBtn.setBorder(null);
		closeBtn.setFocusable(false);
		closeBtn.setContentAreaFilled(false);
		
		icon_minimode = getClass().getResource("/images/icons/minimode.png");
		icon_expand = getClass().getResource("/images/icons/expand.png");
		minimodeBtn = new JButton(new ImageIcon(icon_minimode));
		minimodeBtn.setBorder(null);
		minimodeBtn.setFocusable(false);
		minimodeBtn.setContentAreaFilled(false);
		minimodeBtn.addActionListener(new minimodeBtnAction());
		
		// scroll Bar
		scrollBar=new JScrollPane(list_panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//		scrollBar.setViewportView(list_panel);
	}

	private JComponent buildContentPane() {
		JLayeredPane combo = new JLayeredPane();
		/* Actually build process is almost done at this */
		makeContents();

		/* Set position & size (x, y, width, height) */
		dragArea.setBounds(1,1,348,25);
		minimiseBtn.setBounds(286, 3, 21, 21);
		minimodeBtn.setBounds(307,3,21,21);
		closeBtn.setBounds(328, 3, 21, 21);
		albumArt.setBounds(1, 27, 124, 124);
		info_panel.setBounds(126, 42, 223, 40);
		ctr_panel.setBounds(126, 97, 223, 50);
		dock_panel.setBounds(1, 152, 348, 40);
		list_panel.setBounds(1, 193, 348, 206);
		scrollBar.setBounds(1, 193, 348, 206);
		paint_g.setBounds(0, 0, 350, 400);
		paint_w.setBounds(1,1,348,25);
		paint_w2.setBounds(126, 27, 223, 125);
		paint_w3.setBounds(1, 152, 348, 40);

		/*
		 * Lower loading is top , Higher is bottom but I don't know exactly
		 * about the number
		 */
		combo.add(minimiseBtn, 1);
		combo.add(minimodeBtn, 2);
		combo.add(closeBtn, 3);
		combo.add(dragArea,4);
		combo.add(paint_w, 11);
		combo.add(albumArt, 5);
		combo.add(info_panel, 6);
		combo.add(ctr_panel, 7);
		combo.add(paint_w2, 12);
		combo.add(dock_panel, 8);
		combo.add(paint_w3, 13);
		combo.add(list_panel, 9);
		combo.add(scrollBar, 10);
		combo.add(paint_g, 16);
		return combo;
	}	

	public static void main(String[] args) throws Exception {
		/* Default UI is windows */
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		// make and visible
		SMPlayer = new MusicPlayer();
		SMPlayer.setVisible(true);
	}

	public JPanel paint(Color c) {
		JPanel paint = new JPanel();
		paint.setBackground(c);
		return paint;
	}

	class MinimizeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setExtendedState(ICONIFIED);
		}
	}

	class CloseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (play_mp3 != null) {
				play_mp3.exit();
			}
			dispose();
		}
	}
	class minimodeBtnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(MusicPlayer.SMPlayer.getHeight()==192) {
				MusicPlayer.SMPlayer.setSize(350,400);
				minimodeBtn.setIcon(new ImageIcon(icon_expand));
			}
			else {
				MusicPlayer.SMPlayer.setSize(350, 192);
				minimodeBtn.setIcon(new ImageIcon(icon_minimode));
			}
		}
	}
}