package bubtjobs.com.hungama.Others;

/**
 * Created by Murtuza on 3/13/2016.
 */
public class Common_Url {
    //private String commonPart="http://bubtjobs.com/hungama/";
     private String commonPart="http://10.0.3.2:8080/hungama/"; // genymotion
    // private String commonPart="http://10.0.2.2:8080/hungama/"; //


    //url
    public String videoUrl(){
        return commonPart+"api/getVideoList";
    }
    public String newMusicUrl(){
        return commonPart+"api/getNewMusicList";
    }
    public String popularMusicUrl(){
        return commonPart+"api/getPopularMusicList";
    }
    public String signUp(){
        return commonPart+"api/singup";
    }
    public String signIn(){
        return commonPart+"api/singin";
    }
    public String signInGooglePlus(){
        return commonPart+"api/singup_WithGooglePlus";
        //return "http://bubtjobs.com/hungama/api/singup_WithGooglePlus";
    }

    //path
    public String imageVideo(){
        return commonPart+"image/video/";
    }
    public String videoPath(){
        return commonPart+"video/";
    }

    public String imageNewMusic(){
        return commonPart+"image/NewMusic/";
    }

    public String musicPath(){
        return commonPart+"NewMusic/";
    }

    public String imagepopularMusic(){
        return commonPart+"image/popularMusic/";
    }

    public String popularMusicPath(){
        return commonPart+"popularMusic/";
    }



//    //url
//    public String videoUrl(){
//        //return "http://bubtjobs.com/hungama/api/getVideoList";
//        return "http://10.0.3.2:8080/hungama/api/getVideoList";
//    }
//    public String newMusicUrl(){
//        //return "http://bubtjobs.com/hungama/api/getNewMusicList";
//        return "http://10.0.3.2:8080/hungama/api/getNewMusicList";
//    }
//    public String popularMusicUrl(){
//        //return "http://bubtjobs.com/hungama/api/getPopularMusicList";
//        return "http://10.0.3.2:8080/hungama/api/getPopularMusicList";
//    }
//    public String signUp(){
//        // return "http://bubtjobs.com/hungama/api/singup";
//        return "http://10.0.3.2:8080/hungama/api/singup";
//    }
//    public String signIn(){
//        //return "http://bubtjobs.com/hungama/api/singin";
//        return "http://10.0.3.2:8080/hungama/api/singin";
//    }
//    public String signInGooglePlus(){
//        //return "http://bubtjobs.com/hungama/api/singup_WithGooglePlus";
//        return "http://10.0.3.2:8080/hungama/api/singup_WithGooglePlus";
//    }
//
//    //path
//    public String imageVideo(){
//        // return "http://bubtjobs.com/hungama/image/video/";
//        return "http://10.0.3.2:8080/hungama/image/video/";
//    }
//    public String videoPath(){
//        //return "http://bubtjobs.com/hungama/video/";
//        return "http://10.0.3.2:8080/hungama/video/";
//
//    }
//
//    public String imageNewMusic(){
//        //return "http://bubtjobs.com/hungama/image/NewMusic/";
//        return "http://10.0.3.2:8080/hungama/image/NewMusic/";
//    }
//
//    public String musicPath(){
//        //return "http://bubtjobs.com/hungama/NewMusic/";
//        return "http://10.0.3.2:8080/hungama/NewMusic/";
//
//    }
//
//    public String imagepopularMusic(){
//        //return "http://bubtjobs.com/hungama/image/popularMusic/";
//        return "http://10.0.3.2:8080/hungama/image/popularMusic/";
//    }
//
//    public String popularMusicPath(){
//        //return "http://bubtjobs.com/hungama/popularMusic/";
//        return "http://10.0.3.2:8080/hungama/popularMusic/";
//
//    }




}
