package bubtjobs.com.hungama.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bubtjobs.com.hungama.Adapter.SongAdapter;
import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;

public class PlayList extends ListActivity {
    DataBaseManager dataBaseManager;
    NewMusic newMusic;
    ArrayList<NewMusic> musicArrayList;
    String movie_code="";
    SessionManager sessionManager;

    ListView sampleLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        init();
        sessionManager=new SessionManager(this);
//        if()
//        musicArrayList=dataBaseManager.makeAudioPlayList(movie_code);

        if(sessionManager.getAudioMusicType().equals("popularMusic"))
        {
            musicArrayList=dataBaseManager.makeAudioPlayList(movie_code,"popularMusicList");
        }
        else{
            musicArrayList=dataBaseManager.makeAudioPlayList(movie_code,"newMusicList");
        }

        if(musicArrayList!=null && musicArrayList.size()>0)
        {
            SongAdapter adapter=new SongAdapter(getApplicationContext(),musicArrayList);
            setListAdapter(adapter);
            ListView lv = getListView();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String fileName=musicArrayList.get(position).getFileName();
                    Intent intent=new Intent(getApplicationContext(),AudioPlayer.class);
                    intent.putExtra("position",position);
                    setResult(2, intent);
                    finish();
                }
            });
        }
    }

    public void init(){

        Intent intent=getIntent();
        movie_code=intent.getStringExtra("movie_code");
        Log.i("movie_code",movie_code);
        dataBaseManager=new DataBaseManager(PlayList.this);
        musicArrayList=new ArrayList<>();

    }
}
