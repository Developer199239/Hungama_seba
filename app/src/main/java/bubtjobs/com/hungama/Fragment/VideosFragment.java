package bubtjobs.com.hungama.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bubtjobs.com.hungama.Adapter.VideoAdapter;
import bubtjobs.com.hungama.Model.Video;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;


public class VideosFragment extends Fragment{

    SessionManager sessionManager;
    ListView songListView;

    Video video;
    ArrayList<Video> videoArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_videos, container, false);
        songListView=(ListView)view.findViewById(R.id.songListView);
        init();

        if(videoArrayList!=null && videoArrayList.size()>0)
        {
            VideoAdapter adpater=new VideoAdapter(getActivity(),videoArrayList);
            songListView.setAdapter(adpater);
        }
        else{
            Toast.makeText(getActivity(), "Vido List not found", Toast.LENGTH_SHORT).show();
        }


        return view;

    }
    public void init(){
       videoArrayList=new ArrayList<>();
        video=new Video("1","test","test");
        videoArrayList.add(video);
        video=new Video("2","jalilur","song");
        videoArrayList.add(video);
        video=new Video("3","song","song1");
        videoArrayList.add(video);
    }

}
