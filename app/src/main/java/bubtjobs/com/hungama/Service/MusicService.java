package bubtjobs.com.hungama.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.util.ArrayList;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;
import android.app.Notification;
import android.app.PendingIntent;

import bubtjobs.com.hungama.Activity.AudioPlayer;
import bubtjobs.com.hungama.Activity.Home;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;

/**
 * Created by Murtuza on 4/9/2016.
 */
public class MusicService  extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnBufferingUpdateListener {

    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<NewMusic> songs;
    //current position
    private int songPosn;
    private final IBinder musicBind = new MusicBinder();

    SessionManager sessionManager;

    private String songTitle="ok";
    private static final int NOTIFY_ID=1;
    private boolean shuffle=false,repeat=false;
    private Random rand;
    private int mBufferPosition;

    Common_Url common_url;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize position
        songPosn=0;
        //create player
        rand=new Random();
        player = new MediaPlayer();
        common_url=new Common_Url();
        sessionManager=new SessionManager(getApplicationContext());
        initMusicPlayer();
    }
    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void initMusicPlayer(){
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }


    public void setList(ArrayList<NewMusic> theSongs){
        songs=theSongs;
    }

    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    public int reSongPos(){
       return songPosn;
    }

    public void playSong(){
        player.reset();
        //get song
        NewMusic playSong = songs.get(songPosn);
        songTitle=playSong.getSongName();
//get id
        //long currSong = playSong.getID();
        String fileName=playSong.getFileName();

        String audioPath=common_url.musicPath()+fileName+".mp3";

        Log.i("audioPath",audioPath);
//set uri
//        Uri trackUri = ContentUris.withAppendedId(
//                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                currSong);
        try{

            player.setDataSource(getApplicationContext(), Uri.parse(audioPath));
           // sessionManager.setDelay("0");
            //player.setDataSource(audioPath);

        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
       // sessionManager.setDelay("0");
    }




    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        setBufferPosition(percent * getDur() / 100);
    }


    protected void setBufferPosition(int progress) {
        mBufferPosition = progress;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

       player.start();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        sessionManager.setAudioLoad("1");
        if(player.getCurrentPosition()>0){
            mp.reset();
            playNext();
        }



    }




    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        sessionManager.setAudioLoad("0");

        Intent notIntent = new Intent(this, AudioPlayer.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.app_icon)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);



//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setAutoCancel(false);
//        builder.setDefaults(Notification.DEFAULT_ALL);
//        builder.setWhen(System.currentTimeMillis());
//        builder.setContentTitle("ok");
//
//        builder.setSmallIcon(R.drawable.app_icon);
//        builder.setTicker("title");
//        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        Intent intent = new Intent(this, Home.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        Intent actionIntent = new Intent(this, Home.class);
//        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.addAction(android.R.drawable.ic_media_pause, "PAUSE", actionPendingIntent);
//
//// if(artwork != null) {
////     builder.setLargeIcon(artwork);
//// }
//// builder.setContentText(artist);
//// builder.setSubText(album);
//
//// startForeground(R.id.notification_id, builder.build());
//        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//       // manager.nontify(R.id.notification_id, builder.build());
//        manager.notify(NOTIFY_ID,builder.build());
    }

    public int getBufferPercentage() {
        return mBufferPosition;
    }
    public int getPosn(){
        return player.getCurrentPosition();
    }

    public int getDur(){

        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }




    public void pausePlayer() {
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }
    public void playPrev(){
        sessionManager.setAudioLoad("1");
        if(repeat)
        {
            playSong();
        }
        else {
            if(shuffle)
            {

                int newSong = songPosn;
                while(newSong==songPosn){
                    newSong=rand.nextInt(songs.size());
                }
                songPosn=newSong;
                playSong();
            }
            else {
                songPosn--;
                if (songPosn < 0) songPosn = songs.size() - 1;
                playSong();
            }
        }
    }


    //skip to next
    public void playNext() {
        sessionManager.setAudioLoad("1");
        if (repeat) {
            playSong();
        }
        else {

        if(shuffle){
            songPosn++;
            if (songPosn > songs.size() - 1) songPosn = 0;
            int newSong = songPosn;
            while(newSong==songPosn){
                newSong=rand.nextInt(songs.size());
            }
            songPosn=newSong;
            playSong();
        }
        else{
            songPosn++;
            if(songPosn==songs.size()) songPosn=0;

            Log.i("nextSong",""+songPosn);
            playSong();
        }

        }
    }


    public void setSuffle(){
        if(shuffle) shuffle=false;
        else {
            shuffle = true;
            repeat=false;
        }
    }
    public void setRepeat(){
        if(repeat) repeat=false;
        else {
            repeat = true;
            shuffle=false;
        }

    }


    public String getSongTitle(){
        return songTitle;
    }


    public void seekupdate(){

    }
}