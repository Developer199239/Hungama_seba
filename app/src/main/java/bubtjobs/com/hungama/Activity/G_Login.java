package bubtjobs.com.hungama.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import bubtjobs.com.hungama.Others.AlertDialogManager;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Volly.AppController;

public class G_Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks {
    GoogleApiClient google_api_client;
    GoogleApiAvailability google_api_availability;
    SignInButton signIn_btn;
    private static final int SIGN_IN_CODE = 0;
    private static final int PROFILE_PIC_SIZE = 120;
    private ConnectionResult connection_result;
    private boolean is_intent_inprogress;
    private boolean is_signInBtn_clicked;
    private int request_code;
    ProgressDialog progress_dialog;
    Common_Url common_url;
    AlertDialogManager alertDialogManager;
    private ProgressDialog pd;
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buidNewGoogleApiClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g__login);

        custimizeSignBtn();
        setBtnClickListeners();
        progress_dialog = new ProgressDialog(this);
        sessionManager=new SessionManager(G_Login.this);
        common_url=new Common_Url();
        alertDialogManager=new AlertDialogManager();
        progress_dialog.setMessage("Signing in....");
    }
    private void buidNewGoogleApiClient(){

        google_api_client =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API,Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    private void custimizeSignBtn(){

        signIn_btn = (SignInButton) findViewById(R.id.sign_in_button);
        signIn_btn.setSize(SignInButton.SIZE_STANDARD);
        signIn_btn.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});

    }
    private void setBtnClickListeners(){
        signIn_btn.setOnClickListener(this);
       // gPlusSignIn();
    }
    protected void onStart() {
        super.onStart();
        google_api_client.connect();
    }

    protected void onStop() {
        super.onStop();
        if (google_api_client.isConnected()) {
            gPlusSignOut();
            google_api_client.disconnect();
        }
    }

    protected void onResume(){
        super.onResume();
        if (google_api_client.isConnected()) {
            google_api_client.connect();
        }
        //gPlusSignIn();
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!is_intent_inprogress) {

            connection_result = result;

            if (is_signInBtn_clicked) {

                resolveSignInError();
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        // Check which request we're responding to
        if (requestCode == SIGN_IN_CODE) {
            request_code = requestCode;
            if (responseCode != RESULT_OK) {
                is_signInBtn_clicked = false;
                progress_dialog.dismiss();

            }

            is_intent_inprogress = false;

            if (!google_api_client.isConnecting()) {
                google_api_client.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        is_signInBtn_clicked = false;
        // Get user's information and set it into the layout
        getProfileInfo();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        google_api_client.connect();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                gPlusSignIn();
               // singInGoogle("jalil","t@t.t","123");
                break;
        }
    }
    private void gPlusSignIn() {
        if (!google_api_client.isConnecting()) {
            Log.d("user connected", "connected");
            is_signInBtn_clicked = true;
            progress_dialog.show();
            resolveSignInError();

        }
    }
    private void gPlusSignOut() {
        if (google_api_client.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(google_api_client);
            google_api_client.disconnect();
            google_api_client.connect();
        }
    }
    private void resolveSignInError() {
        if (connection_result.hasResolution()) {
            try {
                is_intent_inprogress = true;
                connection_result.startResolutionForResult(this, SIGN_IN_CODE);
                Log.d("resolve error", "sign in error resolved");
            } catch (IntentSender.SendIntentException e) {
                is_intent_inprogress = false;
                google_api_client.connect();
            }
        }
    }

    private void getProfileInfo() {

        try {

            if (Plus.PeopleApi.getCurrentPerson(google_api_client) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(google_api_client);
                setPersonalInfo(currentPerson);

            } else {
                Toast.makeText(getApplicationContext(),
                        "No Personal info mention", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setPersonalInfo(Person currentPerson){

        String personName = currentPerson.getDisplayName();
        String token=currentPerson.getId();
        String email = Plus.AccountApi.getAccountName(google_api_client);
        //gPlusSignOut();
        //progress_dialog.dismiss();
       //Toast.makeText(this, personName+" "+token+" "+email, Toast.LENGTH_LONG).show();
        new SignInGoogleEx(personName,token,email).execute();
        //singInGoogle("jal","tt@yahoo.com","123");


    }

    private class SignInGoogleEx extends AsyncTask<Void,Void,Void> {

        private String name;
        private String token;
        private String email;


        public SignInGoogleEx(String name,String token,String email){
            this.name=name;
            this.email=email;
            this.token=token;
        }

        @Override
        protected Void doInBackground(Void... params) {
            singInGoogle(name,email,token);
            return null;
        }

    }

    public void singInGoogle(final String name, final String email,final String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, common_url.signInGooglePlus(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i("json", obj.toString());
                    if(obj.getInt("success")==1)
                    {
                        String name=obj.getString("name");
                        sessionManager.setUserName(name);
                        handler.sendEmptyMessage(1);
                    }
                    else{
                        handler.sendEmptyMessage(0);
                    }


                } catch (JSONException e) {
                    handler.sendEmptyMessage(0);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(0);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("token", token);
                params.put("name", name);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            progress_dialog.dismiss();
            switch (msg.what){
                case 0:
                    alertDialogManager.showAlertDialog(G_Login.this, "Error", "Try Again", true);
                    startActivity(new Intent(G_Login.this, Login.class));
                    break;
                case 1:
//                    alertDialogManager.showAlertDialog(G_Login.this, "success", "Login Successfully", true);
//                    startActivity(new Intent(G_Login.this,Home.class));
                    AlertDialog.Builder builder = new AlertDialog.Builder(G_Login.this);
                    builder.setMessage("Login Successfully")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(G_Login.this, Home.class);
                                    startActivity(intent);
                                }
                            });

                    builder.create();
                    builder.setCancelable(false);
                    builder.show();
                    break;
                default:
                    break;
            }
        }
    };
}
