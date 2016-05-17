package bubtjobs.com.hungama.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;


public class RadioFragment extends Fragment{

    SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_radio, container, false);
        Log.i("Frag", "Radio");
        return view;
    }

}
