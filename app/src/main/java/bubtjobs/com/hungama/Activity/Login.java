package bubtjobs.com.hungama.Activity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bubtjobs.com.hungama.Adapter.VideoAdapter;
import bubtjobs.com.hungama.Model.Video;
import bubtjobs.com.hungama.Others.CommonFunction;
import bubtjobs.com.hungama.Others.Common_Url;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Volly.AppController;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button signIn_bt,signup_bt;
    CommonFunction commonFunction;
    Common_Url common_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void init(){
        commonFunction=new CommonFunction();
        common_url=new Common_Url();
        signIn_bt=(Button)findViewById(R.id.signIn_bt);
        signup_bt=(Button)findViewById(R.id.sign_bt);

        signIn_bt.setOnClickListener(this);
        signup_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signIn_bt)
        {
            LayoutInflater li = LayoutInflater.from(Login.this);
            View promptsView = li.inflate(R.layout.sign_in, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
            alertDialogBuilder.setView(promptsView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            final TextView email = (TextView) promptsView.findViewById(R.id.email_tv);
            final TextView pass = (TextView) promptsView.findViewById(R.id.password_tv);
            final Button signIn_bt = (Button) promptsView.findViewById(R.id.signIn_bt);

            signIn_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!commonFunction.isEmpty((EditText) email) || !commonFunction.isEmpty((EditText) pass))
                    {
                        Toast.makeText(Login.this, "Insert all fields", Toast.LENGTH_LONG).show();
                    }
                    else if(!commonFunction.isValidEmail((EditText) email))
                    {
                        Toast.makeText(Login.this, "Enter valid email", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Login.this, "Ok", Toast.LENGTH_LONG).show();
                    }
                }
            });

            alertDialog.show();
        }
        else if(v.getId()==R.id.sign_bt)
        {
            LayoutInflater li = LayoutInflater.from(Login.this);
            View promptsView = li.inflate(R.layout.sign_up, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
            alertDialogBuilder.setView(promptsView);
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final TextView name = (TextView) promptsView.findViewById(R.id.name_tv);
            final TextView mobile = (TextView) promptsView.findViewById(R.id.mobile_tv);
            final TextView email = (TextView) promptsView.findViewById(R.id.email_tv);
            final TextView pass = (TextView) promptsView.findViewById(R.id.password_tv);
            final Button singUp = (Button) promptsView.findViewById(R.id.singUp_bt);

            singUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!commonFunction.isEmpty((EditText) name)||!commonFunction.isEmpty((EditText) mobile)||!commonFunction.isEmpty((EditText) email)||!commonFunction.isEmpty((EditText) pass))
                   {
                       Toast.makeText(Login.this, "Insert all fields", Toast.LENGTH_LONG).show();
                   }
                    else if(!commonFunction.isValidEmail((EditText) email))
                   {
                       Toast.makeText(Login.this, "Enter valid email", Toast.LENGTH_LONG).show();
                   }
                    else{
                    new SignUpEx(name.getText().toString(),mobile.getText().toString(),email.getText().toString(),pass.getText().toString()).execute();
                   }
                }
            });


            alertDialog.show();
        }

    }

    private class SignUpEx extends AsyncTask<Void,Void,Void> {

        private String name;
        private String mobile;
        private String email;
        private String password;

        public SignUpEx(String name,String mobile,String email,String password){
           this.name=name;
            this.mobile=mobile;
            this.email=email;
            this.password=password;
        }

        @Override
        protected Void doInBackground(Void... params) {
            singUp(name,mobile,email,password);
            return null;
        }

    }

    public void singUp(final String name, final String mobile, final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, common_url.signUp(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i("json", obj.toString());
                    if(obj.getInt("success")==1)
                    {
                        handler.sendEmptyMessage(1);
                    }
                    else if(obj.getInt("success")==2){
                        handler.sendEmptyMessage(2);
                    }
                    else{
                        handler.sendEmptyMessage(0);
                    }


                } catch (JSONException e) {
                    Toast.makeText(Login.this,""+e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);
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
            switch (msg.what){
                case 0:
                    Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(Login.this, "al ready email", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

}
