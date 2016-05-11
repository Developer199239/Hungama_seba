package bubtjobs.com.hungama.Others;

import android.os.StrictMode;

/**
 * Created by Murtuza on 3/24/2016.
 */
public class NetworkOnMainThreadException {
    public NetworkOnMainThreadException(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
    }

}
