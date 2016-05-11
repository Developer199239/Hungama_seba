package bubtjobs.com.hungama.Others;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Murtuza on 3/11/2016.
 */
public class CommonFunction {

    public CommonFunction(){

    }
    // Email Validation Check
    public  boolean isValidEmail(EditText target)
    {
        if (TextUtils.isEmpty(target.getText().toString())) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target.getText().toString()).matches();
        }
    }
    // Is Empty Check Edittext
    public boolean isEmpty(EditText etText)
    {
        if(etText.getText().toString().trim().length()>0)
            return true;
        return false;
    }
    // check internet connection
    public boolean isNetworkAvailable(Context context){
        ConnectivityManager cManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cManager.getActiveNetworkInfo();

        if(networkInfo!=null)
        {
            if(networkInfo.isAvailable() && networkInfo.isConnected())
            {
                return true;
            }
        }

        return false;
    }
    // Toast Message
    public void toastMessate(Context context,String messasge){
        Toast.makeText(context,messasge,Toast.LENGTH_LONG).show();
    }
}
