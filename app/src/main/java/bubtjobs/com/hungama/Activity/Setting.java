package bubtjobs.com.hungama.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import bubtjobs.com.hungama.R;

public class Setting extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar volumnProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        volumnProgressBar = (SeekBar) findViewById(R.id.volumeProgressBar);
        volumnProgressBar.setProgress(0);
        volumnProgressBar.setMax(14);

        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int origionalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumnProgressBar.setProgress(origionalVolume);


        volumnProgressBar.setOnSeekBarChangeListener(this);


    }

    public void myAccounts(View view) {
        startActivity(new Intent(Setting.this,Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
            int volumn=seekBar.getProgress();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumn, 0);
        volumnProgressBar.setProgress(volumn);
    }
}
