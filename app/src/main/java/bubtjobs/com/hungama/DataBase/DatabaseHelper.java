package bubtjobs.com.hungama.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BITM Trainer 401 on 3/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static  final int DATABASE_VERSION=1;
    static  final String DATABASE_NAME="hungama";

    // table videoPlayList
    static  final String TABLE_VIDEO_PLAY_LIST="videoPlayList";
    static final String COL_ID="id";
    static final String COL_FILE_NAME="fileName";
    static final String COL_SONG_NAME="songName";
    static final String COL_MOVIE_NAME="movieName";
    // table new Music list
    static  final String TABLE_NEW_MUSIC_LIST="newMusicList";
    static final String COL_MOVIE_CODE="movieCode";
    static final String COL_TYPE="type";

    // table audio playList
    static  final String TABLE_AUDIO_PLAY_LIST="audioPlayList";

    //crate table video play list
   static final String CREATE_VIDEO_PLAY_LIST=" CREATE TABLE " + TABLE_VIDEO_PLAY_LIST + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_FILE_NAME +" TEXT," +COL_SONG_NAME +" TEXT,"+COL_MOVIE_NAME +" TEXT)";

    //create table newMusic play list
    static final String CREATE_NEW_MUSIC_LIST=" CREATE TABLE " + TABLE_NEW_MUSIC_LIST + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_FILE_NAME +" TEXT," +COL_SONG_NAME +" TEXT,"+COL_MOVIE_NAME +" TEXT,"+COL_TYPE +" TEXT,"+COL_MOVIE_CODE +" TEXT)";

    // create table audio play list
    static final String CREATE_AUDIO_PLAY_LIST=" CREATE TABLE " + TABLE_AUDIO_PLAY_LIST + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_FILE_NAME +" TEXT," +COL_SONG_NAME +" TEXT,"+COL_MOVIE_NAME +" TEXT,"+COL_TYPE +" TEXT,"+COL_MOVIE_CODE +" TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VIDEO_PLAY_LIST);
        db.execSQL(CREATE_NEW_MUSIC_LIST);
        db.execSQL(CREATE_AUDIO_PLAY_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
