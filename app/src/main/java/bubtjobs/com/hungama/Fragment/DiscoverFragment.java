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


public class DiscoverFragment extends Fragment{

    SessionManager sessionManager;
    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Frag", "Discover");
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

}
