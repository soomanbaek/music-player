package action;

import java.awt.Image;

import process.MusicPlayer;

class Update {
	public void songInfo() {
		Get get = new Get();
		get.metaData(true);
		String singerName = get.singerName();
		String songName = get.songName();
		Image albumArt = get.albumArt();
		
		MusicPlayer.info_panel.removeAll();
		MusicPlayer.info_panel.getSongInfo(songName, singerName);
		MusicPlayer.info_panel.loadInfoPanel();
		MusicPlayer.info_panel.updateUI();
		
		MusicPlayer.albumArt.changeBg(albumArt);
	}
	public void songInfo(String songName, String singerName) {
		MusicPlayer.info_panel.removeAll();
		MusicPlayer.info_panel.getSongInfo(songName, singerName);
		MusicPlayer.info_panel.loadInfoPanel();
		MusicPlayer.info_panel.updateUI();
	}
	}