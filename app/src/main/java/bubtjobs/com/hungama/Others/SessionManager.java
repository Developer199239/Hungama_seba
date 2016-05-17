package bubtjobs.com.hungama.Others;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	SharedPreferences perf;
	Editor editor;
	Context _context;
	int PRIVATE_MODE=0;
	private static final String PREF_NAME="hungama";
	private static final String CURRENT_FRAGMENT="current_fragment";
	private static final String SONG_TYPE="song_type";
	private static final String AUDIOLOAD="audioLoad";
	private static final String USER_NAME="user_name";
	private static final String AUDIO_PLAYER_STATUS="audio_player";
	private static final String AUDIO_MUSIC_TYPE="audio_music_type";
	private static final String RUNNING_MUSIC_ALBUM="running_music_album";

	//constructor
	public SessionManager(Context _context) {
		this._context = _context;
		perf=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor=perf.edit();
	}

	//
	public void setCurrentFragment(String frag){
		editor.putString(CURRENT_FRAGMENT,frag);
		editor.commit();
	}
	public String getCurrentFragment(){
		return perf.getString(CURRENT_FRAGMENT,"0");
	}

	public void setMusicType(String songType){
		editor.putString(SONG_TYPE,songType);
		editor.commit();
	}
	public String getMusicType(){
		return perf.getString(SONG_TYPE,"Bengali");
	}

	public void setAudioLoad(String load){
		editor.putString(AUDIOLOAD,load);
		editor.commit();
	}
	public  Boolean getAudioLoad(){
		String delay= perf.getString(AUDIOLOAD,"1");
		if(delay.equals("1"))
			return true;
		else
			return false;
	}

	public void setUserName(String name){
		editor.putString(USER_NAME,name);
		editor.commit();
	}
	public  String getUserName(){
		String name= perf.getString(USER_NAME,"Guest(Sign In)");
		return name;
	}

	public void setAudioPlayerStatus(String status){
		editor.putString(AUDIO_PLAYER_STATUS,status);
		editor.commit();
	}
	public  Boolean getAudioPlayerStatus(){
		String delay= perf.getString(AUDIO_PLAYER_STATUS,"off");
		if(delay.equals("off"))
			return false;
		else
			return true;
	}

	// for url that newMusic or popular music
	public void setAudioMusicType(String name){
		editor.putString(AUDIO_MUSIC_TYPE,name);
		editor.commit();
	}
	public  String getAudioMusicType(){
		String name= perf.getString(AUDIO_MUSIC_TYPE,"newMusic");
		return name;
	}

	// for url that newMusic or popular music
	public void setRunningMusicAlbum(String name){
		editor.putString(RUNNING_MUSIC_ALBUM,name);
		editor.commit();
	}
	public  String getRunningMusicAlbume(){
		String name= perf.getString(RUNNING_MUSIC_ALBUM,"#");
		return name;
	}
    
	

}
