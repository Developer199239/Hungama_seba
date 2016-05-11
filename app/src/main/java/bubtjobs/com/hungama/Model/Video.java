package bubtjobs.com.hungama.Model;

/**
 * Created by Murtuza on 5/11/2016.
 */
public class Video {
    private String id;
    private String type;
    private String fileName;
    private String songName;
    private String movieName;

    public Video(){

    }

    public Video(String fileName, String songName, String movieName) {
        this.fileName = fileName;
        this.songName = songName;
        this.movieName = movieName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
