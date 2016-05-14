package bubtjobs.com.hungama.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.Others.Utilities;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Service.MusicService;
import butterknife.Bind;
import butterknife.OnClick;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    DataBaseManager dataBaseManager;
    NewMusic newMusic;
    ArrayList<NewMusic> musicArrayList;
    String movie_code="";

    ImageButton songListBt,shuffleBt,repeatBt,preBt,playBt,nextBt;
    private SeekBar songProgressBar;
    TextView songStartTv,songEndTv,songTitleTv;

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private Handler mHandler = new Handler();
    private Utilities utils;
    boolean isFirstActivity=true,sessionData=false,seseionShuffle=false,sessionRepeat=false;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    String title="";
    int postion=0;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);


        init();

        musicArrayList=dataBaseManager.makeAudioPlayList(movie_code);
        if(musicArrayList!=null && musicArrayList.size()>0)
        {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }
    @Override
    protected void onDestroy() {
       // sessionManager.setId(String.valueOf(musicSrv.reSongPos()),isShuffle,isRepeat);
        stopService(playIntent);
        musicSrv=null;
        mHandler.removeCallbacks(mUpdateTimeTask);
        super.onDestroy();
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(musicArrayList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    private void init() {

        utils = new Utilities();
        sessionManager=new SessionManager(this);
        isFirstActivity=true;sessionData=false;seseionShuffle=false;sessionRepeat=false;

        Intent intent=getIntent();
         movie_code=intent.getStringExtra("movie_code");
        dataBaseManager=new DataBaseManager(MusicPlayer.this);
        musicArrayList=new ArrayList<>();

        songListBt=(ImageButton)findViewById(R.id.songListBt);
        preBt=(ImageButton)findViewById(R.id.preBt);
        playBt=(ImageButton)findViewById(R.id.playBt);
        nextBt=(ImageButton)findViewById(R.id.nextBt);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);

        songStartTv=(TextView)findViewById(R.id.songStartTv);
        songEndTv=(TextView)findViewById(R.id.songEndTv);

        songListBt.setOnClickListener(this);
        preBt.setOnClickListener(this);
        playBt.setOnClickListener(this);
        nextBt.setOnClickListener(this);
        songProgressBar.setOnSeekBarChangeListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.songListBt:
                Intent intent=new Intent(MusicPlayer.this,PlayList.class);
                intent.putExtra("movie_code",movie_code);
                startActivity(intent);
                break;

            case R.id.preBt:
                Toast.makeText(MusicPlayer.this, "preBt", Toast.LENGTH_SHORT).show();
                break;

            case R.id.playBt:
                Toast.makeText(MusicPlayer.this, "playBt", Toast.LENGTH_SHORT).show();
                musicSrv.setSong(0);
                musicSrv.playSong();
                isFirstActivity = false;
                songProgressBar.setProgress(0);
                songProgressBar.setMax(100);
                updateProgressBar();
                break;

            case R.id.nextBt:
                Toast.makeText(MusicPlayer.this, "nextBt", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = musicSrv.getDur();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        musicSrv.seek(currentPosition);

        // update timer progress again
        updateProgressBar();
    }


    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = musicSrv.getDur();
            long currentDuration = musicSrv.getPosn();

            // Displaying Total Duration time
            songEndTv.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songStartTv.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);
            if(title.equals(musicSrv.getSongTitle()))
            {

            }
            else{
                songTitleTv.setText(musicSrv.getSongTitle());
            }
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 1000);

        }
    };
}
