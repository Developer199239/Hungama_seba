package bubtjobs.com.hungama.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private static int SPLASH_TIME_OUT = 2000;  //1000 x second
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        sessionManager=new SessionManager(MainActivity.this);
        sessionManager.setUserName("Guest(Sign In)");
        sessionManager.setAudioPlayerStatus("off");
        sessionManager.setRunningMusicAlbum("#");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
