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

	//constructor
	public SessionManager(Context _context) {
		this._context = _context;
		perf=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor=perf.edit();
	}

	// emergency Number
	public void setCurrentFragment(String frag){
		editor.putString(CURRENT_FRAGMENT,frag);
		editor.commit();
	}
	public String getCurrentFragment(){
		return perf.getString(CURRENT_FRAGMENT,"0");
	}
	


    
	

}
