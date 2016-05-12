package bubtjobs.com.hungama.Model;

/**
 * Created by Murtuza on 5/12/2016.
 */
public class NewMusic {
    private String id;
    private String type;
    private String fileName;
    private String songName;
    private String movieName;
    private String movie_code;

    public NewMusic(){

    }

    public NewMusic(String type, String fileName, String songName, String movieName, String movie_code) {
        this.type = type;
        this.fileName = fileName;
        this.songName = songName;
        this.movieName = movieName;
        this.movie_code = movie_code;
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

    public String getMovie_code() {
        return movie_code;
    }

    public void setMovie_code(String movie_code) {
        this.movie_code = movie_code;
    }
}
