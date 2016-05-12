package bubtjobs.com.hungama.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.Serializable;
import java.util.ArrayList;

import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.Video;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.R;

public class VideoPlayer extends AppCompatActivity{
    Switch autoPlay_switch;
    VideoView videoView;
    TextView songName_tx,movieName_tx;
    int postion=1,lastIndex=1;
    String fileName="";
    String movieName="";
    String songName="";
    private ArrayList<Video> videoList;
    DataBaseManager dataBaseManager;
    Common_Url common_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoList=new ArrayList<>();
        dataBaseManager=new DataBaseManager(VideoPlayer.this);
        common_url=new Common_Url();

        Intent intent=getIntent();

        postion=Integer.parseInt(intent.getStringExtra("postion"));
        lastIndex=postion;
        fileName=intent.getStringExtra("fileName");
        songName=intent.getStringExtra("songName");
        movieName=intent.getStringExtra("movieName");

        setTitle("Video Player");
        autoPlay_switch=(Switch)findViewById(R.id.autoPlay_switch);
        videoView=(VideoView)findViewById(R.id.videoView);

        songName_tx=(TextView)findViewById(R.id.songName_tx);
        movieName_tx=(TextView)findViewById(R.id.movieName_tx);

        songName_tx.setText("Song Name: "+songName);
        movieName_tx.setText("Movie Name: "+movieName);


       // String videoSongPath="http://bubtjobs.com/hungama/video/"+fileName+".3gp";
         String videoSongPath=common_url.videoPath()+fileName+".3gp";

        videoView.setVideoPath(videoSongPath);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        // play list retrive start
        videoList=dataBaseManager.getVideoList();
        if(videoList!=null && videoList.size()>0)
        {
            lastIndex=videoList.size();
        }
        //play list retrive end


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


                if (autoPlay_switch.isChecked()) {
                    postion++;
                    if(postion==lastIndex)
                    {
                       postion=0;
                    }

                    String fileName=videoList.get(postion).getFileName();

                    songName_tx.setText("Song Name: "+videoList.get(postion).getSongName());
                    movieName_tx.setText("Movie Name: "+videoList.get(postion).getMovieName());

                    String videoSongPath=common_url.videoPath()+fileName+".3gp";
                    videoView.setVideoPath(videoSongPath);
                    MediaController mediaController = new
                            MediaController(VideoPlayer.this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.start();
                } else {

                    LayoutInflater li = LayoutInflater.from(VideoPlayer.this);
                    View promptsView = li.inflate(R.layout.reload_video, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VideoPlayer.this);
                    alertDialogBuilder.setView(promptsView);
                    ImageView relaod_Iv = (ImageView) promptsView.findViewById(R.id.relaod_Iv);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    relaod_Iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            videoView.start();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();


                   // Toast.makeText(VideoPlayer.this, "Auto Play Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //if(autoPlay_switch.isChecked())

    }
}
