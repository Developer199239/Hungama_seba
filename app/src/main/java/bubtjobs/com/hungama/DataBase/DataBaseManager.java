package bubtjobs.com.hungama.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import bubtjobs.com.hungama.Model.Video;

/**
 * Created by Mobile App Develop on 20-4-16.
 */
public class DataBaseManager {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    public DataBaseManager(Context context){
        helper=new DatabaseHelper(context);

    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }
    public boolean addVideoList(ArrayList<Video> arrayList){
        try{
            this.open();
            database.execSQL("DROP TABLE IF EXISTS " +DatabaseHelper.TABLE_VIDEO_PLAY_LIST);

            database.execSQL(DatabaseHelper.CREATE_VIDEO_PLAY_LIST);

            for(Video obj:arrayList)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COL_FILE_NAME,obj.getFileName());
                contentValues.put(DatabaseHelper.COL_SONG_NAME,obj.getSongName());
                contentValues.put(DatabaseHelper.COL_MOVIE_NAME,obj.getMovieName());
                long inserted = database.insert(DatabaseHelper.TABLE_VIDEO_PLAY_LIST, null, contentValues);

            }


            this.close();
            return true;
        }
        catch (Exception e){
            return  false;
        }

    }

    public ArrayList<Video> getVideoList(){
        this.open();
        ArrayList<Video> videoList=new ArrayList<>();
        Video video;
        String query="SELECT * FROM "+DatabaseHelper.TABLE_VIDEO_PLAY_LIST;
        Cursor cursor=database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor!=null && cursor.getCount()>0){
            for(int i=0;i<cursor.getCount();i++)
            {
                String fileName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILE_NAME));
                String songName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SONG_NAME));
                String movieName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOVIE_NAME));
               video=new Video(fileName,songName,movieName);
                videoList.add(video);
                cursor.moveToNext();
            }
        }
        this.close();
        return videoList;
    }
}
