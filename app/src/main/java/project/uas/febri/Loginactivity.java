package project.uas.febri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.febri.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;
import session.SessionManager;

public class Loginactivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private Button btnLinkRegister;
    private Button btnLinkLogin;
    private Button btnlogin;

    private EditText edtUsername, edtPassword;
    private SessionManager session;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        mRequestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().hide();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(this);

        if (session.isLoggedIn()){
            Intent i = new Intent(Loginactivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword =(EditText) findViewById(R.id.edtPassword);

        btnlogin = (Button) findViewById(R.id.btnlogin);

        btnLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Loginactivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnpm= edtUsername.getText().toString();
                String strPass= edtPassword.getText().toString();

                if(strnpm.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if(strPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    Login(strnpm, strPass);
                }
            }
        });

    }

    private void Login(String user, String pass){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Username", user);
        params.put("Password", pass);

        pDialog.setMessage("Mohon Tunggu");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.Login, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("eror");
                            String msg;
                            if (status == true) {
                                msg = response.getString("pesan");
                            } else {
                                session.setLogin(true);
                                msg = response.getString("pesan");
                                Intent i = new Intent(Loginactivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();

                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        //ApplicationController.getInstance().addToRequestQueue(req);

        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
