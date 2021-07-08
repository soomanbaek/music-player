package action;

import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import process.Mp3Player;
import process.MusicPlayer;

public class PlaySong {
	Update update = new Update();

	public void readySong() {
		// Remove Current Player if exist
		if (MusicPlayer.play_mp3 != null) {
			MusicPlayer.play_mp3.exit();
		}

		FileInputStream songStream;
		String currentSongDir;
		try {
			currentSongDir = MusicPlayer.list.get(MusicPlayer.queue);
			songStream = new FileInputStream(currentSongDir);
			MusicPlayer.play_mp3 = new Mp3Player(songStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playSong() {
		try {
			update.songInfo();
			MusicPlayer.play_mp3.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void playPrevious() {
		if (MusicPlayer.queue > 0) {
			MusicPlayer.queue--;
			MusicPlayer.changed = true;
			readySong();
			update.songInfo();
			playSong();
		}
	}

	public void playNext() {
		if (MusicPlayer.queue + 1 < MusicPlayer.list.size()) {
			MusicPlayer.queue++;
			MusicPlayer.changed = true;
			readySong();
			update.songInfo();
			playSong();
		} else {
			MusicPlayer.queue = 0;
			MusicPlayer.changed = true;
			readySong();
			update.songInfo();
			playSong();
		}
	}
}