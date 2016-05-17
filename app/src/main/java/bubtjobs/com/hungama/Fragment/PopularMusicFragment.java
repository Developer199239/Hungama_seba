package bubtjobs.com.hungama.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import bubtjobs.com.hungama.Activity.Home;
import bubtjobs.com.hungama.Adapter.NewMusicAdapter;
import bubtjobs.com.hungama.Adapter.PopularMusicAdapter;
import bubtjobs.com.hungama.DataBase.DataBaseManager;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.Others.AlertDialogManager;
import bubtjobs.com.hungama.Others.CommonFunction;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Volly.AppController;


public class PopularMusicFragment extends Fragment{

    SessionManager sessionManager;
    CommonFunction commonFunction;
    AlertDialogManager alertDialogManager;
    Common_Url common_url;
    ListView songListView;
    DataBaseManager dataBaseManager;

    NewMusic newMusic;
    ArrayList<NewMusic> musicArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_new_music, container, false);
        songListView=(ListView)view.findViewById(R.id.songListView);

        init();

        return  view;
    }

    public void init(){
        commonFunction=new CommonFunction();
        alertDialogManager=new AlertDialogManager();
        common_url=new Common_Url();
        dataBaseManager=new DataBaseManager(getActivity());
        sessionManager=new SessionManager(getActivity());
        musicArrayList=new ArrayList<>();


        if(commonFunction.isNetworkAvailable(getActivity()))
        {
            new newMusicList().execute();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No Internet Connection")
                    .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent=new Intent(getActivity(), Home.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();

        }

    }
    private class newMusicList extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getNewMusicList();
            return null;
        }

    }

    public void getNewMusicList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, common_url.popularMusicUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i("json", obj.toString());
                    if(obj.getInt("success")==1)
                    {
                        String type=sessionManager.getMusicType();

                        JSONArray songlist=obj.optJSONArray("songrow");

                        for(int i=0;i<songlist.length();i++)
                        {
                            JSONObject songRow=songlist.getJSONObject(i);

                            String id=songRow.getString("id");
                            String fileName=songRow.getString("fileName");
                            String songName=songRow.getString("songName");
                            String movieName=songRow.getString("movieName");
                            String movie_code=songRow.getString("movie_code");

                            newMusic=new NewMusic(type,fileName,songName,movieName,movie_code);
                            musicArrayList.add(newMusic);

                        }

                        if(musicArrayList!=null && musicArrayList.size()>0)
                        {
                            boolean isinsert=dataBaseManager.addPopularMusicList(musicArrayList);

                            if(isinsert)
                            {
                                musicArrayList=new ArrayList<>();
                                musicArrayList=dataBaseManager.getSingleMovieName("popularMusicList");
                                if(musicArrayList!=null && musicArrayList.size()>0)
                                {
                                    //Toast.makeText(getActivity(), ""+musicArrayList.size(), Toast.LENGTH_SHORT).show();
                                    PopularMusicAdapter adapter=new PopularMusicAdapter(getActivity(),musicArrayList);
                                    songListView.setAdapter(adapter);

                                }
                                else{
                                    Toast.makeText(getActivity(), "Popular Music List not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "Popular Music List not found", Toast.LENGTH_SHORT).show();
                            }

//                            NewMusicAdapter adpater=new NewMusicAdapter(getActivity(),musicArrayList);
//                            songListView.setAdapter(adpater);
                        }
                        else{
                            Toast.makeText(getActivity(), "Popular Music List not found", Toast.LENGTH_SHORT).show();
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
                params.put("type", sessionManager.getMusicType());
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
