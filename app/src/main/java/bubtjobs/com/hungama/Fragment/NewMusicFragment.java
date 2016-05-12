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


public class NewMusicFragment extends Fragment{

    SessionManager sessionManager;
    CommonFunction commonFunction;
    AlertDialogManager alertDialogManager;
    Common_Url common_url;
    ListView songListView;
    DataBaseManager dataBaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_new_music, container, false);

        init();

        return view;
    }
    public void init(){
        commonFunction=new CommonFunction();
        alertDialogManager=new AlertDialogManager();
        common_url=new Common_Url();
        dataBaseManager=new DataBaseManager(getActivity());
        sessionManager=new SessionManager(getActivity());

        if(commonFunction.isNetworkAvailable(getActivity()))
        {
            new newMusicList().execute();
        }
        else{
            alertDialogManager.showAlertDialog(getContext(),"Error","No InterNet",true);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, common_url.newMusicUrl(), new Response.Listener<String>() {
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
                            String movie_code=songRow.getString("movie_code");

//                            video=new Video(fileName,songName,movieName);
//                            videoArrayList.add(video);

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
