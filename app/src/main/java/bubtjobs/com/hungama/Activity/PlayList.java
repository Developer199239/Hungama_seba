package bubtjobs.com.hungama.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import bubtjobs.com.hungama.Adapter.SongAdapter;
import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.R;

public class PlayList extends ListActivity {
    DataBaseManager dataBaseManager;
    NewMusic newMusic;
    ArrayList<NewMusic> musicArrayList;
    String movie_code="";

    ListView sampleLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        init();

        musicArrayList=dataBaseManager.makeAudioPlayList(movie_code);
        if(musicArrayList!=null && musicArrayList.size()>0)
        {
            SongAdapter adapter=new SongAdapter(getApplicationContext(),musicArrayList);
            setListAdapter(adapter);
            ListView lv = getListView();
        }
    }

    public void init(){

        Intent intent=getIntent();
        movie_code=intent.getStringExtra("movie_code");
        dataBaseManager=new DataBaseManager(PlayList.this);
        musicArrayList=new ArrayList<>();

    }
}
