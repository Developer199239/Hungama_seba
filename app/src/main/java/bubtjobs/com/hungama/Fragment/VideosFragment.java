package bubtjobs.com.hungama.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bubtjobs.com.hungama.Adapter.VideoAdapter;
import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.Video;
import bubtjobs.com.hungama.Others.AlertDialogManager;
import bubtjobs.com.hungama.Others.CommonFunction;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Volly.AppController;


public class VideosFragment extends Fragment{

    SessionManager sessionManager;
    CommonFunction commonFunction;
    AlertDialogManager alertDialogManager;
    Common_Url common_url;
    ListView songListView;
    DataBaseManager dataBaseManager;

    Video video;
    ArrayList<Video> videoArrayList;
    String url="http://bubtjobs.com/hungama/api/getVideoList";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_videos, container, false);
        songListView=(ListView)view.findViewById(R.id.songListView);
        init();
        return view;

    }
    public void init(){
        commonFunction=new CommonFunction();
        alertDialogManager=new AlertDialogManager();
        common_url=new Common_Url();
        dataBaseManager=new DataBaseManager(getActivity());
       videoArrayList=new ArrayList<>();

//
//        video=new Video("1","test","test");
//        videoArrayList.add(video);
//        video=new Video("2","jalilur","song");
//        videoArrayList.add(video);
//        video=new Video("3","song","song1");
 //       videoArrayList.add(video);
        if(commonFunction.isNetworkAvailable(getActivity()))
        {
            new VedioList().execute();
        }
        else{
           alertDialogManager.showAlertDialog(getContext(),"Error","No InterNet",true);
        }

    }

    private class VedioList extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getVidoList();
            return null;
        }

    }
    public void getVidoList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, common_url.videoUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i("json", obj.toString());
                    if(obj.getInt("success")==1)
                    {
                        JSONArray songlist=obj.optJSONArray("songrow");

                        for(int i=0;i<songlist.length();i++)
                        {
                            JSONObject songRow=songlist.getJSONObject(i);

                            String id=songRow.getString("id");
                            String fileName=songRow.getString("fileName");
                            String songName=songRow.getString("songName");
                            String movieName=songRow.getString("movieName");

                            video=new Video(fileName,songName,movieName);
                            videoArrayList.add(video);

                        }



                        //Toast.makeText(getActivity(),"Update Successfully",Toast.LENGTH_LONG).show();

                        if(videoArrayList!=null && videoArrayList.size()>0)
                        {
                            boolean isinsert=dataBaseManager.addVideoList(videoArrayList);
                           // Log.i("insert=== videolist",""+isinsert);
                            VideoAdapter adpater=new VideoAdapter(getActivity(),videoArrayList);
                            songListView.setAdapter(adpater);
                        }
                        else{
                            Toast.makeText(getActivity(), "Vido List not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity(),""+e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "Bengali");
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
