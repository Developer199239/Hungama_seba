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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bubtjobs.com.hungama.Model.Video;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.R;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class VideoAdapter extends ArrayAdapter<Video> {

    private ArrayList<Video> videoList;
    private Context context;
    Common_Url common_url;

    public VideoAdapter(Context context, ArrayList<Video> videoList) {
        super(context, R.layout.video_custom_row, videoList);
        this.context = context;
        this.videoList = videoList;
        common_url=new Common_Url();
    }

    static class ViewHolder {
        ImageView video_img;
        TextView songName_tx,movieName_tx;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.video_custom_row, null);

            viewHolder = new ViewHolder();

            viewHolder.video_img = (ImageView) convertView.findViewById(R.id.video_img);
            viewHolder.songName_tx = (TextView) convertView.findViewById(R.id.songName_tx);
            viewHolder.movieName_tx = (TextView) convertView.findViewById(R.id.movieName_tx);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String imagePath=common_url.imageVideo()+videoList.get(position).getFileName()+".png";

        Log.i("imagePaht",imagePath);

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.not_loading)   // optional
                .error(R.drawable.not_loading)      // optional
                .into(viewHolder.video_img);


        viewHolder.songName_tx.setText(videoList.get(position).getSongName());
        viewHolder.movieName_tx.setText(videoList.get(position).getMovieName());

       // viewHolder.des_tv.setText(tipsList.get(position).getDes());
        return convertView;
    }
}