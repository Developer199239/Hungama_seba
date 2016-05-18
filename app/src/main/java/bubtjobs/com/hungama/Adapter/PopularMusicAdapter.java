package bubtjobs.com.hungama.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bubtjobs.com.hungama.Activity.AudioPlayer;
import bubtjobs.com.hungama.Model.NewMusic;
import bubtjobs.com.hungama.Others.CommonFunction;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class PopularMusicAdapter extends ArrayAdapter<NewMusic> {

    private ArrayList<NewMusic> newMusicList;
    private Context context;
    Common_Url common_url;
    SessionManager sessionManager;
    CommonFunction commonFunction;


    public PopularMusicAdapter(Context context, ArrayList<NewMusic> newMusicList) {
        super(context, R.layout.new_music_custom_row, newMusicList);
        this.context = context;
        this.newMusicList = newMusicList;
        common_url=new Common_Url();
        sessionManager=new SessionManager(context);
        commonFunction=new CommonFunction();

    }

    static class ViewHolder {
        ImageView music_img;
        TextView movieName_tx;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.new_music_custom_row, null);

            viewHolder = new ViewHolder();

            viewHolder.music_img = (ImageView) convertView.findViewById(R.id.music_img);
            viewHolder.movieName_tx = (TextView) convertView.findViewById(R.id.movieName_tx);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }




        String imagePath=common_url.imagepopularMusic()+newMusicList.get(position).getMovie_code()+".png";

        Log.i("imagePaht", imagePath);

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.not_loading)   // optional
                .error(R.drawable.not_loading)      // optional
                .into(viewHolder.music_img);

        viewHolder.movieName_tx.setText(newMusicList.get(position).getMovieName());


        viewHolder.music_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonFunction.isNetworkAvailable(context)) {
                    sessionManager.setAudioMusicType("popularMusic");
                    Intent intent = new Intent(context, AudioPlayer.class);
                    intent.putExtra("movie_code", newMusicList.get(position).getMovie_code());
                    intent.putExtra("running_music_album", "popularMusic" + newMusicList.get(position).getMovie_code());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return convertView;
    }
}
