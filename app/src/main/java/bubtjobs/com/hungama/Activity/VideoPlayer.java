package bubtjobs.com.hungama.Activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import bubtjobs.com.hungama.R;

public class VideoPlayer extends AppCompatActivity {
    Switch autoPlay_switch;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        setTitle("Video Player");
        autoPlay_switch=(Switch)findViewById(R.id.autoPlay_switch);
        videoView=(VideoView)findViewById(R.id.videoView);

        videoView.setVideoPath("http://bubtjobs.com/mp3/b.3gp");
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


                if (autoPlay_switch.isChecked()) {
                    videoView.setVideoPath("http://bubtjobs.com/mp3/b.3gp");
                    MediaController mediaController = new
                            MediaController(VideoPlayer.this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.start();
                } else {
                    Toast.makeText(VideoPlayer.this, "Auto Play Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //if(autoPlay_switch.isChecked())

    }
}
