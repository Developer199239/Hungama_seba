package bubtjobs.com.hungama.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bubtjobs.com.hungama.R;

public class MainActivity extends AppCompatActivity {
    Button go_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void go(View view) {
        startActivity(new Intent(MainActivity.this,Home.class));
    }
}
