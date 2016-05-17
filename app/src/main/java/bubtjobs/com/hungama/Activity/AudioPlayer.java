package bubtjobs.com.hungama.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class AudioPlayer extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    ImageButton songListBt,shuffleBt,repeatBt,preBt,playBt,nextBt;
    ImageView background;
    TextView songStartTv,songEndTv,songTitleTv;
    private SeekBar songProgressBar;

    DataBaseManager dataBaseManager;
    private ArrayList<NewMusic> songList;
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

    String movie_code="";

    boolean a=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        init();
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
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
                musicSrv.setList(songList);
                musicBound = true;

            sessionManager.setAudioLoad("1");
            musicSrv.setSong(0);
            musicSrv.playSong();
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();


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

        sessionManager.setAudioPlayerStatus("on");

        songListBt=(ImageButton)findViewById(R.id.songListBt);
        shuffleBt=(ImageButton)findViewById(R.id.shuffleBt);
        repeatBt=(ImageButton)findViewById(R.id.repeatBt);
        preBt=(ImageButton)findViewById(R.id.preBt);
        playBt=(ImageButton)findViewById(R.id.playBt);
        nextBt=(ImageButton)findViewById(R.id.nextBt);
        background=(ImageView)findViewById(R.id.bk);

        songStartTv=(TextView)findViewById(R.id.songStartTv);
        songEndTv=(TextView)findViewById(R.id.songEndTv);
        songTitleTv=(TextView)findViewById(R.id.songTitleTv);

        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);

        songListBt.setOnClickListener(this);
        shuffleBt.setOnClickListener(this);
        repeatBt.setOnClickListener(this);
        preBt.setOnClickListener(this);
        playBt.setOnClickListener(this);
        nextBt.setOnClickListener(this);
        songProgressBar.setOnSeekBarChangeListener(this);
        songList = new ArrayList<NewMusic>();
        getSongList();



    }

    public void getSongList() {
        Intent intent=getIntent();
        movie_code=intent.getStringExtra("movie_code");
            dataBaseManager=new DataBaseManager(AudioPlayer.this);
            if(sessionManager.getAudioMusicType().equals("popularMusic"))
            {
                songList=dataBaseManager.makeAudioPlayList(movie_code,"popularMusicList");
            }
             else{
                songList=dataBaseManager.makeAudioPlayList(movie_code,"newMusicList");
            }


            if(songList!=null )
            {


            }


    }



    @Override
    public void onClick(View v) {

         if(v.getId()==R.id.songListBt)
        {
            Intent intent=new Intent(AudioPlayer.this,PlayList.class);
            intent.putExtra("movie_code",movie_code);
            startActivityForResult(intent,1);
        }

         else if(v.getId()==R.id.shuffleBt)
        {

        }
        else if(v.getId()==R.id.repeatBt)
        {

        }
        else if(v.getId()==R.id.preBt)
        {
            //Toast.makeText(AudioPlayer.this, "Pre", Toast.LENGTH_SHORT).show();

            musicSrv.playPrev();
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
        }
        else if(v.getId()==R.id.playBt)
        {
            if(musicSrv.isPng())
        {
            musicSrv.pausePlayer();
            playBt.setImageResource(R.drawable.play);
        }
        else
        {
            musicSrv.go();
            updateProgressBar();
            playBt.setImageResource(R.drawable.pause);

        }


        }
        else if(v.getId()==R.id.nextBt)
        {
            musicSrv.playNext();

            //songProgressBar.clearFocus();
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();

            //Toast.makeText(AudioPlayer.this, "next", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 2000);

    }
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if(sessionManager.getAudioLoad())
            {
               // new Delay().execute();
            }
            else{
                long totalDuration = musicSrv.getDur();
                long currentDuration = musicSrv.getPosn();
                //
            // Displaying Total Duration time
            songEndTv.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songStartTv.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
           // Log.d("Progress", "" + progress +" "+totalDuration+" "+currentDuration);
            songProgressBar.setProgress(progress);
            }






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




        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audio_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.homeBack) {
           Intent intent=new Intent(AudioPlayer.this,Home.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
        {

            int position = data.getExtras().getInt("position");
            sessionManager.setAudioLoad("1");
            musicSrv.setSong(position);
            musicSrv.playSong();
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
            playBt.setImageResource(R.drawable.pause);
        }
    }
}
