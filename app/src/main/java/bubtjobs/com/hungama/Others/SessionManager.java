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

	


    
	

}
