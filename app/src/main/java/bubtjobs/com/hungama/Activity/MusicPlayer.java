package bubtjobs.com.hungama.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import bubtjobs.com.hungama.R;

public class MusicPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String movie_code=intent.getStringExtra("movie_code");


        Toast.makeText(MusicPlayer.this, ""+movie_code, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_music_player);
    }
}
