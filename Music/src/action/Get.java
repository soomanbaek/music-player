package action;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

import process.MusicPlayer;

public class Get {
	protected File[] song;
	private File filedir;
	private String singer;
	private String songname;
	private String genre;
	private int duration;
	private Image albumArt;
	private boolean fileSelected;

	public void song() {
		int value = MusicPlayer.browser.showOpenDialog(null);
		if (value == JFileChooser.APPROVE_OPTION) {
			if (MusicPlayer.browser.getSelectedFile().isFile())
				song = MusicPlayer.browser.getSelectedFiles(); // file link
			else {
				filedir = MusicPlayer.browser.getSelectedFile();
				song = filedir.listFiles(new SongFilter());
			}
			fileSelected = true;
		} else
			fileSelected = false;
	}

	public void metaData(boolean encoding) {
		File currentSong = new File(MusicPlayer.list.get(MusicPlayer.queue));
		AudioFile music;
		try {
			music = AudioFileIO.read(currentSong);
			Tag infoTag = music.getTag();

			singer = infoTag.getFirst(FieldKey.ALBUM_ARTIST);
			songname = infoTag.getFirst(FieldKey.TITLE);
			genre = infoTag.getFirst(FieldKey.GENRE);
			duration= music.getAudioHeader().getTrackLength();
			System.out.println(genre);
			final List<Artwork> artworkList = infoTag.getArtworkList();

			if (artworkList.size() > 0) {
				InputStream scraps = new ByteArrayInputStream(infoTag
						.getFirstArtwork().getBinaryData());
				albumArt = ImageIO.read(scraps);
			} else
				albumArt = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (singer != null && songname != null && checkString(singer)
				&& checkString(songname) && encoding) {
			// Encode iso-8859-1 to euc-kr
			try {
				singer = new String(singer.getBytes("iso-8859-1"), "euc-kr");
				songname = new String(songname.getBytes("iso-8859-1"), "euc-kr");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void metaData(int n) {
		File currentSong = new File(MusicPlayer.list.get(n));
			AudioFile music;
			try {
				music = AudioFileIO.read(currentSong);
				Tag infoTag = music.getTag();

				singer = infoTag.getFirst(FieldKey.ALBUM_ARTIST);
				songname = infoTag.getFirst(FieldKey.TITLE);
				genre = infoTag.getFirst(FieldKey.GENRE);
				duration= music.getAudioHeader().getTrackLength();
				System.out.println(genre);
				final List<Artwork> artworkList = infoTag.getArtworkList();

				if (artworkList.size() > 0) {
					InputStream scraps = new ByteArrayInputStream(infoTag
							.getFirstArtwork().getBinaryData());
					albumArt = ImageIO.read(scraps);
				} else
					albumArt = null;

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (singer != null && songname != null && checkString(singer)
					&& checkString(songname)) {
				// Encode iso-8859-1 to euc-kr
				try {
					singer = new String(singer.getBytes("iso-8859-1"), "euc-kr");
					songname = new String(songname.getBytes("iso-8859-1"), "euc-kr");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

	public String singerName() {
		if (singer == "")
			return null;
		else
			return singer;
	}

	public String songName() {
		if (songname == "")
			return null;
		else
			return songname;
	}

	public int duration() {
		return duration;
	}
	public Image albumArt() {
		return albumArt;
	}
	public String genre() {
		return genre;
	}
	public boolean fileSelected() {
		return fileSelected;
	}

	public static boolean checkString(String str) {

		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);

			// ¿µ¹®

			if ((c >= 0x61 && c <= 0x7A) || (c >= 0x41 && c <= 0x5A)) {

			} else {

				return true;

			}

		}

		return false;

	}
}

class SongFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		if (name.toLowerCase().endsWith(".mp3")
				|| name.toLowerCase().endsWith(".wav")
				|| name.toLowerCase().endsWith(".m4a"))
			return true;
		else
			return false;
	}
}