package bubtjobs.com.hungama.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Service.MusicService;

public class Test extends AppCompatActivity {
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sessionManager=new SessionManager(Test.this);
    }

    public void ok(View view) {
        if(sessionManager.getAudioPlayerStatus()){
            //Log.i("On123","on now");
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }




    }

    @Override
    protected void onDestroy() {
        musicSrv=null;
        super.onDestroy();
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
//            //pass list
//            musicSrv.setList(songList);
            musicBound = true;
//
//            sessionManager.setAudioLoad("1");
//            musicSrv.setSong(0);
//            musicSrv.playSong();
//            songProgressBar.setProgress(0);
//            songProgressBar.setMax(100);
//            updateProgressBar();
            startService(playIntent);
            musicSrv.pausePlayer();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };
}
