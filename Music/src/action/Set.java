package action;

import java.io.UnsupportedEncodingException;

import process.MusicPlayer;

public class Set {
	Update update = new Update();
	PlaySong playAction = new PlaySong();
	public void setList(String dir) {
		MusicPlayer.list.add(dir);
		if(MusicPlayer.firstSet) {
			MusicPlayer.firstSet=false;
			update.songInfo();
			playAction.readySong();
		}
	}

	public void song(Get get) {
		if(get.fileSelected()) {
		for (int i = 0; i < get.song.length; i++) {
			setList(get.song[i].getPath());
			get.song[i]=null;
			}
		}
	}
	
	public void fixEncode(String songName, String singerName) {
		try {
			songName = new String(songName.getBytes("ksc5601"), "euc-kr");
			singerName = new String(singerName.getBytes("ksc5601"), "euc-kr");
			update.songInfo(songName, singerName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void volume() {
		
	}
}